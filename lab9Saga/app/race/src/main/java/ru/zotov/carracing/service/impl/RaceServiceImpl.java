package ru.zotov.carracing.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.util.Optional;
import java.util.UUID;


/**
 * @author Created by ZotovES on 18.08.2021
 * Реализация сервиса управления заездами
 */
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
        Race race = raceRepo.save(buildRace(raceTemplate, profileId));

        FuelExpandEvent fuelExpandEvent = FuelExpandEvent.builder()
                .raceId(race.getId())
                .profileId(profileId.toString())
                .fuel(raceTemplate.getFuelConsume()).build();

        kafkaTemplate.send(Constants.KAFKA_RACE_TOPIC, fuelExpandEvent);

        return race;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race start(Long raceId) {
        return changeState(RaceState.START, raceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race finish(Long raceId, String externalId) {
        return changeState(RaceState.FINISH, raceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race cancel(Long raceId) {
        return changeState(RaceState.CANCEL, raceId);
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

    private Race buildRace(RaceTemplate raceTemplate, UUID profileId) {
        return Race.builder()
                .raceTemplateId(raceTemplate.getId())
                .profileId(profileId)
                .state(RaceState.LOAD)
                .build();
    }
}
