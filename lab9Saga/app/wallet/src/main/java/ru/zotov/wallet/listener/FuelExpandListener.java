package ru.zotov.wallet.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.FuelExpandEvent;
import ru.zotov.wallet.service.WalletService;

import java.util.UUID;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;


/**
 * @author Created by ZotovES on 17.08.2021
 * Слушатель события старта заезда
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FuelExpandListener {
    private final WalletService walletService;

    @KafkaListener(topics = Constants.KAFKA_RACE_START_TOPIC, groupId = KAFKA_GROUP_ID)
    public void processMessage(FuelExpandEvent raceStartEvent) {
        log.info(String.format("Received event  -> %s", raceStartEvent));

        walletService.expandFuel(UUID.fromString(raceStartEvent.getProfileId()), raceStartEvent.getFuel(),
                raceStartEvent.getRaceId());
    }
}
