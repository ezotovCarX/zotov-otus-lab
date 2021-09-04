package ru.zotov.carracing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.dto.RaceTemplateDto;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.entity.RaceTemplate;
import ru.zotov.carracing.repo.RaceTemplateRepo;

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
@RequestMapping(value = "templates", produces = APPLICATION_JSON_VALUE)
public class RaceTemplateController {
    private final RaceTemplateRepo raceRepo;
    private final Mapper mapper;

    /**
     * Создать шаблон заезда
     *
     * @param raceTemplate шаблон заезда
     * @return шаблон заезда
     */
    @PostMapping
    public ResponseEntity<RaceTemplateDto> createRaceTemplate(@RequestBody RaceTemplateDto raceTemplate) {
        return Optional.ofNullable(raceTemplate)
                .map(c -> mapper.map(c, RaceTemplate.class))
                .map(raceRepo::save)
                .map(c -> mapper.map(c, RaceTemplateDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить шаблон заезда по ид
     *
     * @param raceId ид шаблона заезда
     * @return шаблон заезда
     */
    @GetMapping("{raceId}")
    public ResponseEntity<RaceTemplateDto> getById(@PathVariable("raceId") Long raceId) {
        return raceRepo.findById(raceId)
                .map(c -> mapper.map(c, RaceTemplateDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить все шаблоны заездов
     *
     * @return заезд
     */
    @GetMapping
    public ResponseEntity<List<RaceTemplateDto>> getAllRaceTemplates() {
        List<RaceTemplateDto> raceDtos = raceRepo.findAll().stream()
                .map(r -> mapper.map(r, RaceTemplateDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(raceDtos);
    }

    /**
     * Редактировать шаблон заезда по ид
     *
     * @param raceId ид шаблон заезда
     * @return шаблон заезда
     */
    @PutMapping("{raceId}")
    public ResponseEntity<RaceTemplateDto> updateRaceTemplate(@PathVariable("raceId") Long raceId, @RequestBody
            RaceTemplateDto race) {
        return raceRepo.findById(raceId)
                .map(c -> mapper.map(c, RaceTemplate.class))
                .map(updateFieldRaceTemplate(race))
                .map(raceRepo::save)
                .map(c -> mapper.map(c, RaceTemplateDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Удалить шаблон заезда по ид
     *
     * @param raceId ид шаблон заезда
     */
    @DeleteMapping("{raceId}")
    public ResponseEntity<Void> deleteById(@PathVariable("raceId") Long raceId) {
        raceRepo.deleteById(raceId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновление полей шаблона заезда
     *
     * @param incomeRace дто шаблона заезда
     */
    private Function<RaceTemplate, RaceTemplate> updateFieldRaceTemplate(RaceTemplateDto incomeRace) {
        return race -> {
            race.setName(incomeRace.getName());
            race.setRewardId(incomeRace.getRewardId());
            race.setTrackId(incomeRace.getTrackId());

            return race;
        };
    }
}
