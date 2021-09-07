package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 07.09.2021
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMailEvent {
    private String email;
    private String messageText;
}
