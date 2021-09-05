package ru.zotov.wallet.service;

import org.springframework.lang.NonNull;
import ru.zotov.wallet.entity.Wallet;

import java.util.Optional;
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
    void expandFuel(@NonNull UUID profileId, @NonNull Integer fuel, @NonNull Long raceId);

    void addFuel(@NonNull UUID profileId, @NonNull Integer fuel);

    void createWallet(@NonNull UUID profileId);

    Optional<Wallet> getWallet();

    void toReward(UUID profileId, Long rewardId);

    void returnReward(UUID profileId, Long rewardId);
}
