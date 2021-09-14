package ru.zotov.carracing.profile.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.CreatePlayerEvent;
import ru.zotov.carracing.profile.service.ProfileService;

/**
 * @author Created by ZotovES on 14.09.2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreateProfileListener {
    private final ProfileService profileService;

    @KafkaListener(topics = Constants.KAFKA_CREATE_PROFILE_TOPIC, groupId = Constants.KAFKA_GROUP_ID)
    public void onProcessMessage(CreatePlayerEvent createPlayerEvent) {
        log.info("Событие создание профиля игрока ");

        profileService.create(createPlayerEvent.getProfileId());
    }
}
