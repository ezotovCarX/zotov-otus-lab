package ru.zotov.wallet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.dto.WalletDto;
import ru.zotov.wallet.service.WalletService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 03.09.2021
 * Контроллер для получения
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "wallet", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class WalletController {
    private final WalletService walletService;
    private final Mapper mapper;

    /**
     * Получить кошелек игрока
     *
     * @return кошелек игрока
     */
    @GetMapping
    public ResponseEntity<WalletDto> getPlayerWallet() {
        return walletService.getWallet()
                .map(w -> mapper.map(w, WalletDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
