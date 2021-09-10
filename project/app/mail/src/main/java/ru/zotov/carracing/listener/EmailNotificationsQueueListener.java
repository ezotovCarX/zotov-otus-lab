package ru.zotov.carracing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.SendMailEvent;
import ru.zotov.carracing.service.EmailService;

/**
 * @author Created by ZotovES on 10.09.2021
 * Слушатель событий на отправку писем
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationsQueueListener {
    private final EmailService emailService;

    @KafkaListener(topics = Constants.KAFKA_SEND_MAIL_TOPIC, groupId = Constants.KAFKA_GROUP_ID)
    public void processNewNotificationEvent(SendMailEvent message) {
        log.info("Received event on send mail");
        emailService.sendMessage(message);
    }
}
