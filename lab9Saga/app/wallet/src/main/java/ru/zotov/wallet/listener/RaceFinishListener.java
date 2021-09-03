package ru.zotov.wallet.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.RaceFinishEvent;
import ru.zotov.carracing.event.RewardEvent;
import ru.zotov.wallet.service.WalletService;

import java.util.UUID;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;

/**
 * @author Created by ZotovES on 03.09.2021
 * Слушатель команды завершения заезда
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RaceFinishListener {
    private final WalletService walletService;

    @KafkaListener(topics = Constants.KAFKA_TO_REWARD_TOPIC, groupId = KAFKA_GROUP_ID)
    public void processMessage(RewardEvent rewardEvent) {
        log.info(String.format("Received event  -> %s", rewardEvent));

        walletService.toReward(UUID.fromString(rewardEvent.getProfileId()), rewardEvent.getRewardId());
    }

    @KafkaListener(topics = Constants.KAFKA_RETURN_REWARD_TOPIC, groupId = KAFKA_GROUP_ID)
    public void returnReward(RewardEvent rewardEvent) {
        log.info(String.format("Received event  -> %s", rewardEvent));

        walletService.returnReward(UUID.fromString(rewardEvent.getProfileId()), rewardEvent.getRewardId());
    }
}
