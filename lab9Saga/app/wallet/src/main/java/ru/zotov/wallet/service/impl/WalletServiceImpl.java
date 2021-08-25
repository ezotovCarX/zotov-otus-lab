package ru.zotov.wallet.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.FuelExpandSuccessEvent;
import ru.zotov.wallet.repo.WalletRepo;
import ru.zotov.wallet.service.WalletService;


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

    /**
     * Расход топлива
     *
     * @param profileId ид профиля игрока
     * @param fuel      кол-во топлива
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void expandFuel(@NonNull Long profileId, @NonNull Integer fuel, @NonNull Long raceId) {
        walletRepo.findByProfileId(profileId)
                .filter(profile -> profile.getFuel() >= fuel)
                .ifPresentOrElse(profile -> {
                    log.info(String.format("Списываем топливо  -> %s", fuel));
                    profile.setFuel(profile.getFuel() - fuel);
                    walletRepo.save(profile);
                    FuelExpandSuccessEvent expandSuccessEvent = new FuelExpandSuccessEvent(raceId, profileId, fuel);
                    log.info("Отправляем сообщение об успешном списании топлива ");
                    kafkaTemplate.send(Constants.KAFKA_RACE_TOPIC, expandSuccessEvent);
                }, sendFailExpandFuel(profileId, fuel, raceId));

    }

    private Runnable sendFailExpandFuel(@NonNull Long profileId, @NonNull Integer fuel, @NonNull Long raceId) {
        return () -> {
            log.info("Отправляем сообщение об ошибке списания топлива ");
            FuelExpandSuccessEvent expandFailEvent = new FuelExpandSuccessEvent(raceId, profileId, fuel);
            kafkaTemplate.send(Constants.KAFKA_RACE_TOPIC, expandFailEvent);
        };
    }
}
