package ru.zotov.carracing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Created by ZotovES on 10.09.2021
 */
@Configuration
public class MailConfig {
    @Bean
    JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = mailSender.getJavaMailProperties();
        mailSender.setHost("smtp.yandex.ru");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setUsername("carracingtest@yandex.ru");
        mailSender.setPassword("unsjpbzedouhdqfx");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
