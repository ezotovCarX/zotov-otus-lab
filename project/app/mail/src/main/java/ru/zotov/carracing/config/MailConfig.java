package ru.zotov.carracing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Created by ZotovES on 10.09.2021
 * Конфиг отправителя почты
 */
@Configuration
public class MailConfig {
    @Value("${spring.mail.host:smtp.yandex.ru}")

    private String host;
    @Value("${spring.mail.port:465}")
    private Integer port;
    @Value("${spring.mail.username:carracingtest@yandex.ru}")
    private String username;
    @Value("${spring.mail.password:unsjpbzedouhdqfx}")
    private String password;
    @Value("${spring.mail.protocol:smtps}")
    private String protocol;

    @Bean
    JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = mailSender.getJavaMailProperties();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
