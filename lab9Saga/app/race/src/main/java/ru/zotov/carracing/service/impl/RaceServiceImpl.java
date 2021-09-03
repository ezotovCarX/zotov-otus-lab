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
import ru.zotov.carracing.event.RaceFinishEvent;
import ru.zotov.carracing.event.RewardEvent;
import ru.zotov.carracing.repo.RaceRepo;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.carracing.service.RaceService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;


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

        race.map(buildMessage(profileId))
                .ifPresent(fuelExpandEvent -> kafkaTemplate.send(Constants.KAFKA_RACE_START_TOPIC, fuelExpandEvent)
                        .addCallback(m -> log.info("Send complete"), e -> {
                            throw new KafkaException("Send message error");
                        }));

        return race.orElseThrow();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race start(Long raceId) {
        Optional<Race> race = raceRepo.findById(raceId);
        return race
                .filter(r -> RaceState.LOADED.equals(r.getState()))
                .map(startRace())
                .map(changeState(RaceState.START))
                .orElse(race.orElseThrow());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Race> findById(Long id) {
        return raceRepo.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race finish(Long raceId, String externalId) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        Race race = Optional.ofNullable(externalId)
                .map(UUID::fromString)
                .flatMap(externalUuid -> raceRepo.findById(raceId)
                        .filter(r -> profileId.equals(r.getProfileId()))
                        .filter(r -> externalUuid.equals(r.getExternalId()))
                        .filter(r -> RaceState.START.equals(r.getState()))
                        .map(changeState(RaceState.FINISH_SUCCESS)))
                .orElseThrow();

        log.info("Отправляем сообщение о выдаче награды");
        kafkaTemplate.send(Constants.KAFKA_TO_REWARD_TOPIC, buildRewardEvent(race));
        if (race.getRaceTemplate().getCheckOnCheat()) {
            log.info("Отправляем результаты заезда на проверку в Античит");
            kafkaTemplate.send(Constants.KAFKA_RACE_FINISH_TOPIC, buildFinishEvent(race));
        }
        return race;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race cancel(Long raceId) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        Optional<Race> race = raceRepo.findById(raceId)
                .filter(r -> !RaceState.FINISH_SUCCESS.equals(r.getState()));

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
    public void changeState(RaceState state, Long raceId) {
        Optional<Race> raceOptional = raceRepo.findById(raceId);
        raceOptional.ifPresent(race -> {
            race.setState(state);
            raceRepo.save(race);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeState(RaceState state, UUID externalRaceId) {
        Optional<Race> raceOptional = raceRepo.findByExternalId(externalRaceId);
        raceOptional.ifPresent(race -> {
            race.setState(state);
            raceRepo.save(race);
        });
    }

    private Function<RaceTemplate, Race> buildRace(UUID profileId) {
        return raceTemplate -> Race.builder()
                .raceTemplate(raceTemplate)
                .profileId(profileId)
                .state(RaceState.LOAD)
                .build();
    }

    private Function<Race, Race> startRace() {
        return race -> {
            race.setExternalId(UUID.randomUUID());
            race.setRaceStartTime(ZonedDateTime.now().toEpochSecond());
            return race;
        };
    }

    private RaceFinishEvent buildFinishEvent(Race race) {
        return RaceFinishEvent.builder()
                .profileId(race.getProfileId().toString())
                .externalId(requireNonNull(race.getExternalId()).toString())
                .startTime(race.getRaceStartTime())
                .rewardId(race.getRaceTemplate().getRewardId())
                .finishTime(ZonedDateTime.now().toEpochSecond())
                .build();
    }

    private Function<Race, Race> changeState(RaceState raceState) {
        return race -> {
            race.setState(raceState);
            return raceRepo.save(race);
        };
    }

    private RewardEvent buildRewardEvent(Race race) {
        return RewardEvent.builder()
                .rewardId(race.getRaceTemplate().getRewardId())
                .profileId(race.getProfileId().toString())
                .build();
    }

    private Function<Race, FuelExpandEvent> buildMessage(UUID profileId) {
        return race -> FuelExpandEvent.builder()
                .raceId(race.getId())
                .profileId(profileId.toString())
                .fuel(race.getRaceTemplate().getFuelConsume()).build();
    }
}
