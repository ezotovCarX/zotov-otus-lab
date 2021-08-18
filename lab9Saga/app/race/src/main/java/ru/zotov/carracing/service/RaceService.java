package ru.zotov.carracing.service;

import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.RaceTemplate;

/**
 * @author Created by ZotovES on 17.08.2021
 * Сервис управления заездами
 */
public interface RaceService {
    Race createRace(RaceTemplate raceTemplate);

    Race start(Long raceId);

    Race finish(Long raceId, String externalId);

    Race cancel(Long raceId);
}
