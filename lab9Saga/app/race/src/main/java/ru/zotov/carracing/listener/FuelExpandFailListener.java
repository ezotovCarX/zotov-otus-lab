package ru.zotov.carracing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.enums.RaceState;
import ru.zotov.carracing.event.FuelExpandFailedEvent;
import ru.zotov.carracing.event.FuelExpandSuccessEvent;
import ru.zotov.carracing.service.RaceService;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;
import static ru.zotov.carracing.enums.RaceState.LOADED;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FuelExpandFailListener {
    private final RaceService raceService;

//    @KafkaListener(topics = Constants.EXPAND_FUEL_FAIL_KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
//    public void processMessage(FuelExpandFailedEvent fuelExpandFailedEvent) {
//        log.info(String.format("Received event  -> %s", fuelExpandFailedEvent));
//
//        raceService.changeState(RaceState.LOAD_FAILED, fuelExpandFailedEvent.getRaceId());
//        log.info("Топливо не списалось");
//    }
}
