package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlayerEvent {
    private String nickname;
    private UUID profileId;
    private String email;
}
