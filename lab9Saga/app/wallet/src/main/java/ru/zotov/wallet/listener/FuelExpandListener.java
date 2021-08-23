package ru.zotov.wallet.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.FuelExpandEvent;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;


/**
 * @author Created by ZotovES on 17.08.2021
 * Слушатель события старта заезда
 */
@Slf4j
@Component
public class FuelExpandListener {
    @KafkaListener(topics = Constants.KAFKA_RACE_TOPIC, groupId = KAFKA_GROUP_ID)
    public void processMessage(FuelExpandEvent raceStartEvent) {
        log.info(String.format("Received event  -> %s", raceStartEvent));
        log.info(String.format("Списываем топливо  -> %s", raceStartEvent.getFuel()));
    }
}
