package ru.zotov.wallet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.FuelExpandFailedEvent;
import ru.zotov.carracing.event.FuelExpandSuccessEvent;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.wallet.entity.Wallet;
import ru.zotov.wallet.repo.RewardRepo;
import ru.zotov.wallet.repo.WalletRepo;
import ru.zotov.wallet.service.WalletService;

import java.util.Optional;
import java.util.UUID;


/**
 * @author Created by ZotovES on 23.08.2021
 * Реализация сервиса управления кошельком
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private static final int DEFAULT_FUEL = 3;
    private static final int DEFAULT_MONEY = 10000;
    private final WalletRepo walletRepo;
    private final RewardRepo rewardRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SecurityService securityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createWallet(@NonNull UUID profileId) {
        Wallet wallet = Wallet.builder()
                .profileId(profileId)
                .fuel(DEFAULT_FUEL)
                .money(DEFAULT_MONEY)
                .build();

        walletRepo.save(wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Wallet> getWallet() {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        return walletRepo.findByProfileId(profileId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toReward(UUID profileId, Long rewardId) {
        rewardRepo.findById(rewardId)
                .ifPresent(reward -> {
                    walletRepo.findByProfileId(profileId)
                            .ifPresent(wallet -> {
                                wallet.setMoney(wallet.getMoney() + reward.getTotal());
                                walletRepo.save(wallet);
                            });
                });
    }

    @Override
    public void returnReward(UUID profileId, Long rewardId) {
        rewardRepo.findById(rewardId)
                .ifPresent(reward -> {
                    walletRepo.findByProfileId(profileId)
                            .ifPresent(wallet -> {
                                wallet.setMoney(wallet.getMoney() - reward.getTotal());
                                walletRepo.save(wallet);
                            });
                });
    }

    /**
     * Расход топлива
     *
     * @param profileId ид профиля игрока
     * @param fuel      кол-во топлива
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expandFuel(@NonNull UUID profileId, @NonNull Integer fuel, @NonNull Long raceId) {
        walletRepo.findByProfileId(profileId)
                .filter(wallet -> wallet.getFuel() >= fuel)
                .ifPresentOrElse(wallet -> {
                    log.info(String.format("Списываем топливо  -> %s", fuel));
                    wallet.setFuel(wallet.getFuel() - fuel);
                    walletRepo.save(wallet);
                    if (fuel > 0) {
                        FuelExpandSuccessEvent expandSuccessEvent =
                                new FuelExpandSuccessEvent(raceId, profileId.toString(), fuel);
                        log.info("Отправляем сообщение об успешном списании топлива ");
                        kafkaTemplate.send(Constants.EXPAND_FUEL_SUCCESS_KAFKA_TOPIC, expandSuccessEvent);
                    }
                }, sendFailExpandFuel(profileId.toString(), fuel, raceId));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFuel(@NonNull UUID profileId, @NonNull Integer fuel) {
        walletRepo.findByProfileId(profileId)
                .ifPresent(wallet -> {
                    Integer persistFuel = wallet.getFuel();
                    log.info(String.format("Добавляем топливо %s + %s", persistFuel, fuel));
                    wallet.setFuel(persistFuel + fuel);
                    log.info(walletRepo.save(wallet).toString());
                });
    }

    private Runnable sendFailExpandFuel(@NonNull String profileId, @NonNull Integer fuel, @NonNull Long raceId) {
        return () -> {
            log.info("Отправляем сообщение об ошибке списания топлива ");
            FuelExpandFailedEvent expandFailEvent = new FuelExpandFailedEvent(raceId, profileId, fuel);
            kafkaTemplate.send(Constants.EXPAND_FUEL_FAIL_KAFKA_TOPIC, expandFailEvent);
        };
    }
}
