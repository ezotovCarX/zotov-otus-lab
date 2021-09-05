package ru.zotov.carracing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.enums.RaceState;
import ru.zotov.carracing.service.RaceService;

import java.util.UUID;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;


/**
 * @author Created by ZotovES on 17.08.2021
 * Слушатель событий заездов
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FailCheatReviewListener {
    private final RaceService raceService;

    @KafkaListener(topics = Constants.KAFKA_FAIL_CHEAT_REVIEW_TOPIC, groupId = KAFKA_GROUP_ID)
    public void processMessage(String externalRaceId) {
        log.info(String.format("Received event  -> %s", externalRaceId));

        raceService.changeState(RaceState.FINISH_FAILED, UUID.fromString(externalRaceId));
        log.info("Отменить результаты заезда");
    }
}
