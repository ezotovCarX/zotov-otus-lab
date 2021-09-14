package ru.zotov.carracing.profile.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.RaceFinishEvent;
import ru.zotov.carracing.profile.service.ProfileService;

import java.util.UUID;

/**
 * @author Created by ZotovES on 14.09.2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RaceCancelListener {
    private final ProfileService profileService;

    @KafkaListener(topics = Constants.PROFILE_REGRESS, groupId = Constants.KAFKA_GROUP_ID)
    public void onProcessMessage(RaceFinishEvent raceFinishEvent) {
        log.info("Поступило событие отмены результатов заезда");

        profileService.regressProgress(UUID.fromString(raceFinishEvent.getProfileId()),
                raceFinishEvent.getFinishTime() - raceFinishEvent.getStartTime());
    }
}
