package ru.zotov.wallet.service;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * @author Created by ZotovES on 23.08.2021
 * Сервис управления кошельком игрока
 */
public interface WalletService {
    /**
     * Расход топлива
     *
     * @param profileId ид профиля игрока
     * @param fuel      кол-во топлива
     */
    void expandFuel(@NonNull Long profileId, @NonNull Integer fuel, @NonNull Long raceId);

    void createWallet(@NonNull UUID profileId);
}
