package ru.zotov.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import ru.zotov.auth.service.PlayerService;
import ru.zotov.carracing.common.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.repo.PlayerRepo;
import ru.zotov.carracing.dto.RegisterUserDto;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 28.08.2021
 * Контроллер аутентификации
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class AuthController {
    private final PlayerService playerService;
    private final Mapper mapper;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return Optional.ofNullable(registerUserDto)
                .map(user -> mapper.map(user, Player.class))
                .map(playerService::createPlayer)
                .map(user -> mapper.map(user, RegisterUserDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
