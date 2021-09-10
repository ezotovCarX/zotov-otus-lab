package ru.zotov.carracing.service;


import ru.zotov.carracing.event.SendMailEvent;

public interface EmailService {

    /**
     * Отправить оповещение по электронной почте
     *
     * @param emailNotificationMessage почтовое сообщение
     */
    void sendMessage(SendMailEvent emailNotificationMessage);
}
