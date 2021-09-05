package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 03.09.2021
 * Событие успешного финиша заезда
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardEvent {
    private String profileId;
    private Long rewardId;
}
