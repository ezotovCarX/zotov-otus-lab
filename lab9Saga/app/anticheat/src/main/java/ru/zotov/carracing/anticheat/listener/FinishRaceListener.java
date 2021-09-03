package ru.zotov.carracing.anticheat.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.anticheat.entity.CheatReview;
import ru.zotov.carracing.anticheat.service.CheatReviewService;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.event.RaceFinishEvent;

import java.util.Optional;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;


/**
 * @author Created by ZotovES on 17.08.2021
 * Слушатель событий
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FinishRaceListener {
    private final CheatReviewService cheatReviewService;
    private final Mapper mapper;

    @KafkaListener(topics = Constants.KAFKA_RACE_FINISH_TOPIC, groupId = KAFKA_GROUP_ID)
    public void finishRace(RaceFinishEvent finishEvent) throws InterruptedException {
        log.info(String.format("Received event  -> %s", finishEvent));

        //Эмулируем долгий интеллектуальный алгоритм нейросети
        Thread.sleep(100);
        Optional.of(finishEvent)
                .map(e -> mapper.map(e, CheatReview.class))
                .ifPresent(cheatReviewService::reviewRaceResult);
    }
}
