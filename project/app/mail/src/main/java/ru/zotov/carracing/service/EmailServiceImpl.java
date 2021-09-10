package ru.zotov.carracing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.zotov.carracing.event.SendMailEvent;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 * @author Created by ZotovES on 10.09.2021
 * Реализация сервиса отправки почты
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String SEND_TO = "c5231wcig934@mail.ru";
    private static final String SEND_FROM = "carracingtest@yandex.ru";
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    /**
     * Отправить оповещение по электронной почте
     *
     * @param emailNotificationMessage почтовое сообщение
     */
    @Override
    public void sendMessage(SendMailEvent emailNotificationMessage) {
        log.info("Отправляем email на адрес {}", emailNotificationMessage.getEmail());
        MimeMessagePreparator messagePreparatory = createMimeMessage(emailNotificationMessage);
        javaMailSender.send(messagePreparatory);
    }

    private MimeMessagePreparator createMimeMessage(SendMailEvent emailNotificationMessage) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(SEND_FROM);
            messageHelper.setTo(SEND_TO);
            messageHelper.setText(generateEmailContent(emailNotificationMessage), true);
            messageHelper.setSubject("Access code for enter game CarRacing");
        };
    }

    /**
     * формируем HTML контент для письма
     *
     * @param emailNotificationMessage уведомление по эл. почте
     * @return Сформированный контент для письма
     */
    private String generateEmailContent(SendMailEvent emailNotificationMessage) {
        Context context = new Context();
        context.setVariables(createAndFillValueMap(emailNotificationMessage));
        return templateEngine.process("notification", context);
    }

    private Map<String, Object> createAndFillValueMap(SendMailEvent emailNotificationMessage) {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("message", ofNullable(emailNotificationMessage.getMessageText()).orElse(""));
        return valueMap;
    }
}
