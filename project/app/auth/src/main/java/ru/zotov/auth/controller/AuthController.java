package ru.zotov.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.auth.dto.TokenDto;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.service.PlayerService;
import ru.zotov.auth.service.impl.JwtTokenProvider;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.dto.LoginDto;
import ru.zotov.carracing.dto.RegisterUserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private static final String X_USER_ID = "X-User-Id";
    private static final String X_EMAIL = "X-Email";
    private static final String X_NICKNAME = "X-Nickname";
    private final PlayerService playerService;
    private final Mapper mapper;
    private final JwtTokenProvider tokenProvider;

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return Optional.ofNullable(registerUserDto)
                .map(user -> mapper.map(user, Player.class))
                .map(playerService::createPlayer)
                .map(user -> mapper.map(user, RegisterUserDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return playerService.login(loginDto.getEmail(), loginDto.getPassword())
                .map(user -> mapper.map(user, TokenDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody String refreshToken) {
        return playerService.refreshToken(refreshToken)
                .map(user -> mapper.map(user, TokenDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping
    public ResponseEntity<Void> auth(HttpServletRequest request, HttpServletResponse response) {
        Optional.of(tokenProvider.resolveToken(request))
                .filter(tokenProvider::validateToken)
                .map(tokenProvider::getUserTokenInfo)
                .ifPresent(userTokenInfo -> {
                    response.setHeader(X_USER_ID, userTokenInfo.getUserId());
                    response.setHeader(X_EMAIL, userTokenInfo.getEmail());
                    response.setHeader(X_NICKNAME, userTokenInfo.getNickName());
                });
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/recovery")
    public ResponseEntity<Void> recovery(@RequestBody String email) {
        playerService.recoveryPassword(email);
        return ResponseEntity.ok().build();
    }
}
