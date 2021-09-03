package ru.zotov.carracing.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.dto.RaceFinishDto;
import ru.zotov.carracing.dto.RaceOperationDto;
import ru.zotov.carracing.repo.RaceTemplateRepo;
import ru.zotov.carracing.security.filter.CustomUser;
import ru.zotov.carracing.service.RaceService;

import javax.websocket.server.PathParam;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 17.08.2021
 * Контроллер управления зпездами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "race", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class RaceOperationController {
    private final RaceService raceService;
    private final RaceTemplateRepo raceTemplateRepo;
    private final ModelMapper mapper = new ModelMapper();

    /**
     * Загрузить заезд
     *
     * @return dto заезда
     */
    @PostMapping(value = "/{raceId}/load")
    public ResponseEntity<RaceOperationDto> raceLoad(@PathVariable("raceId") Long raceId) {
        return raceTemplateRepo.findById(raceId)
                .map(raceService::createRace)
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Стартовать заезд
     *
     * @return dto заезда
     */
    @PostMapping("/{raceId}/start")
    public ResponseEntity<RaceOperationDto> raceStart(@PathVariable("raceId") Long raceId) {
        return Optional.of(raceService.start(raceId))
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Финиш заезда
     *
     * @return dto заезда
     */
    @PostMapping("/finish")
    public ResponseEntity<RaceOperationDto> raceFinish(@RequestBody RaceFinishDto raceFinishDto) {
        return Optional.of(raceService.finish(raceFinishDto.getId(), raceFinishDto.getExternalId()))
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Отмена заезда
     *
     * @return dto заезда
     */
    @PostMapping("/{raceId}/cancel")
    public ResponseEntity<RaceOperationDto> raceCancel(@PathVariable("raceId") Long raceId) {
        return Optional.of(raceService.cancel(raceId))
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Получить заезд ро ид
     *
     * @param raceId ид заезда
     * @return dto заезда
     */
    @GetMapping
    public ResponseEntity<RaceOperationDto> getRaceById(@PathParam("raceId") Long raceId) {
        return raceService.findById(raceId)
                .map(race -> mapper.map(race, RaceOperationDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
