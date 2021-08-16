package ru.zotov.carracing.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.RaceDto;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.Reward;
import ru.zotov.carracing.repo.RaceRepo;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 10.08.2021
 * Контроллер заездов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "races", produces = APPLICATION_JSON_VALUE)
public class RaceController {
    private final RaceRepo raceRepo;
    private final Mapper mapper;

    /**
     * Создать заезд
     *
     * @param race заезд
     * @return заезд
     */
    @PostMapping
    public ResponseEntity<RaceDto> createRace(@RequestBody RaceDto race) {
        return Optional.ofNullable(race)
                .map(c -> mapper.map(c, Race.class))
                .map(raceRepo::save)
                .map(c -> mapper.map(c, RaceDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить заезд по ид
     *
     * @param raceId ид заезда
     * @return заезд
     */
    @GetMapping("{raceId}")
    public ResponseEntity<RaceDto> getById(@PathVariable("raceId") Long raceId) {
        return raceRepo.findById(raceId)
                .map(c -> mapper.map(c, RaceDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить все заезды
     *
     * @return заезд
     */
    @GetMapping
    public ResponseEntity<List<RaceDto>> getAllRaces() {
        List<RaceDto> raceDtos = raceRepo.findAll().stream()
                .map(r -> mapper.map(r, RaceDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(raceDtos);
    }

    /**
     * Редактировать заезд по ид
     *
     * @param raceId ид заезда
     * @return заезд
     */
    @PutMapping("{raceId}")
    public ResponseEntity<RaceDto> updateRace(@PathVariable("raceId") Long raceId, @RequestBody RaceDto race) {
        return raceRepo.findById(raceId)
                .map(c -> mapper.map(c, Race.class))
                .map(updateFieldRace(race))
                .map(raceRepo::save)
                .map(c -> mapper.map(c, RaceDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Удалить заезд по ид
     *
     * @param raceId ид заезда
     */
    @DeleteMapping("{raceId}")
    public ResponseEntity<Void> deleteById(@PathVariable("raceId") Long raceId) {
        raceRepo.deleteById(raceId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновление полей заезда
     *
     * @param incomeRace дто заезда
     */
    private Function<Race, Race> updateFieldRace(RaceDto incomeRace) {
        return race -> {
            race.setName(incomeRace.getName());
            race.setReward(Reward.builder().id(incomeRace.getRewardId()).build());
            race.setTrackId(incomeRace.getTrackId());

            return race;
        };
    }
}
