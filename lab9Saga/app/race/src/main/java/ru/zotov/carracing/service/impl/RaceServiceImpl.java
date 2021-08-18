package ru.zotov.carracing.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.RaceTemplate;
import ru.zotov.carracing.enums.RaceState;
import ru.zotov.carracing.event.FuelExpandEvent;
import ru.zotov.carracing.repo.RaceRepo;
import ru.zotov.carracing.service.RaceService;

import javax.transaction.Transactional;

/**
 * @author Created by ZotovES on 18.08.2021
 * Реализация сервиса управления заездами
 */
@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {
    private final RaceRepo raceRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Race createRace(RaceTemplate raceTemplate) {
        Race race = raceRepo.save(Race.builder()
                .raceTemplateId(raceTemplate.getId())
                .state(RaceState.LOAD)
                .build());
        FuelExpandEvent fuelExpandEvent = FuelExpandEvent.builder()
                .raceId(race.getId())
                .fuel(raceTemplate.getFuelConsume()).build();

        kafkaTemplate.send(Constants.KAFKA_RACE_TOPIC, fuelExpandEvent);

        return race;
    }

    @Override
    public Race start(Long raceId) {
        return null;
    }

    @Override
    public Race finish(Long raceId, String externalId) {
        return null;
    }

    @Override
    public Race cancel(Long raceId) {
        return null;
    }
}
