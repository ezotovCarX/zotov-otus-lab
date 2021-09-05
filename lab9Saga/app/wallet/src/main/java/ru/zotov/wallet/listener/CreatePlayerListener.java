package ru.zotov.wallet.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.CreatePlayerEvent;
import ru.zotov.wallet.service.WalletService;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePlayerListener {
    private final WalletService walletService;

    @KafkaListener(topics = Constants.KAFKA_PLAYER_TOPIC, groupId = KAFKA_GROUP_ID)
    public void processMessage(CreatePlayerEvent createPlayerEvent) {
        log.info(String.format("Received event  -> %s", createPlayerEvent));

        walletService.createWallet(createPlayerEvent.getProfileId());
    }
}
