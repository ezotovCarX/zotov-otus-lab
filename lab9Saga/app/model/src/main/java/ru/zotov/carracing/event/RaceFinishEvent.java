package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 03.09.2021
 * Событие финиша заезда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceFinishEvent {
    private String profileId;
    private String externalId;
    private Long startTime;
    private Long finishTime;
    private Long rewardId;
}
