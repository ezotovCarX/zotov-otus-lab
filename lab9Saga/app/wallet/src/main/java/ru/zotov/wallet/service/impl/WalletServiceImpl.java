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
import ru.zotov.wallet.entity.Wallet;
import ru.zotov.wallet.repo.WalletRepo;
import ru.zotov.wallet.service.WalletService;

import java.util.UUID;


/**
 * @author Created by ZotovES on 23.08.2021
 * Реализация сервиса управления кошельком
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepo walletRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void createWallet(@NonNull UUID profileId) {
        Wallet wallet = Wallet.builder()
                .profileId(profileId)
                .fuel(10)
                .money(10000)
                .build();

        walletRepo.save(wallet);
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
                .filter(profile -> profile.getFuel() >= fuel)
                .ifPresentOrElse(profile -> {
                    log.info(String.format("Списываем топливо  -> %s", fuel));
                    profile.setFuel(profile.getFuel() - fuel);
                    walletRepo.save(profile);
                    FuelExpandSuccessEvent expandSuccessEvent = new FuelExpandSuccessEvent(raceId, profileId.toString(), fuel);
                    log.info("Отправляем сообщение об успешном списании топлива ");
                    kafkaTemplate.send(Constants.EXPAND_FUEL_SUCCESS_KAFKA_TOPIC, expandSuccessEvent);
                }, sendFailExpandFuel(profileId.toString(), fuel, raceId));

    }

    private Runnable sendFailExpandFuel(@NonNull String profileId, @NonNull Integer fuel, @NonNull Long raceId) {
        return () -> {
            log.info("Отправляем сообщение об ошибке списания топлива ");
            FuelExpandFailedEvent expandFailEvent = new FuelExpandFailedEvent(raceId, profileId, fuel);
            kafkaTemplate.send(Constants.EXPAND_FUEL_FAIL_KAFKA_TOPIC, expandFailEvent);
        };
    }
}
