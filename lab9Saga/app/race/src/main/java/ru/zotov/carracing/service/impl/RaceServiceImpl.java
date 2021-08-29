package ru.zotov.carracing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.RaceTemplate;
import ru.zotov.carracing.enums.RaceState;
import ru.zotov.carracing.event.FuelExpandEvent;
import ru.zotov.carracing.repo.RaceRepo;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.carracing.service.RaceService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;


/**
 * @author Created by ZotovES on 18.08.2021
 * Реализация сервиса управления заездами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {
    private final SecurityService securityService;
    private final RaceRepo raceRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race createRace(RaceTemplate raceTemplate) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        raceRepo.findByProfileIdAndStateIn(profileId, List.of(RaceState.LOAD, RaceState.LOADED, RaceState.START)).stream()
                .map(changeState(RaceState.CANCEL))
                .filter(r -> Set.of(RaceState.LOADED, RaceState.START).contains(r.getState()))
                .map(buildMessage(profileId))
                .forEach(message -> kafkaTemplate.send(Constants.KAFKA_RACE_CANCEL_TOPIC, message));

        var race = Optional.of(raceTemplate)
                .map(buildRace(profileId));
        race.ifPresent(raceRepo::save);

        race.map(r -> buildMessage(profileId))
                .ifPresent(fuelExpandEvent -> kafkaTemplate.send(Constants.KAFKA_RACE_TOPIC, fuelExpandEvent)
                        .addCallback(m -> log.info("Send complete"), e -> {
                            throw new KafkaException("Send message error");
                        }));

        return race.orElseThrow();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race start(Long raceId) {
        return raceRepo.findById(raceId)
                .filter(r -> RaceState.LOADED.equals(r.getState()))
                .map(changeState(RaceState.START))
                .orElseThrow();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Race> findById(Long id) {
        return raceRepo.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race finish(Long raceId, String externalId) {
        return Optional.ofNullable(externalId)
                .map(UUID::fromString)
                .flatMap(externalUuid -> raceRepo.findById(raceId)
                        .filter(r -> externalUuid.equals(r.getExternalId()))
                        .filter(r -> RaceState.START.equals(r.getState()))
                        .map(changeState(RaceState.FINISH)))
                .orElseThrow();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race cancel(Long raceId) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        Optional<Race> race = raceRepo.findById(raceId)
                .filter(r -> !RaceState.FINISH.equals(r.getState()));

        race.filter(r -> RaceState.LOADED.equals(r.getState()))
                .map(r -> buildMessage(profileId))
                .ifPresent(fuelExpandEvent -> kafkaTemplate.send(Constants.KAFKA_RACE_CANCEL_TOPIC, fuelExpandEvent)
                        .addCallback(m -> log.info("Send complete"), e -> {
                            throw new KafkaException("Send message error");
                        }));
        return race.map(changeState(RaceState.CANCEL)).orElseThrow();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race changeState(RaceState state, Long raceId) {
        Optional<Race> raceOptional = raceRepo.findById(raceId);
        raceOptional.ifPresent(race -> {
            race.setState(state);
            raceRepo.save(race);
        });

        return raceOptional.orElseThrow();
    }

    private Function<RaceTemplate, Race> buildRace(UUID profileId) {
        return raceTemplate -> Race.builder()
                .raceTemplate(raceTemplate)
                .profileId(profileId)
                .state(RaceState.LOAD)
                .build();
    }

    private Function<Race, Race> changeState(RaceState raceState) {
        return race -> {
            if (raceState == RaceState.START) {
                race.setExternalId(UUID.randomUUID());
            }
            race.setState(raceState);
            return raceRepo.save(race);
        };
    }

    private Function<Race, FuelExpandEvent> buildMessage(UUID profileId) {
        return race -> FuelExpandEvent.builder()
                .raceId(race.getId())
                .profileId(profileId.toString())
                .fuel(race.getRaceTemplate().getFuelConsume()).build();
    }
}
