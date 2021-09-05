package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 17.08.2021
 * Событие неудачного списания топлива
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuelExpandFailedEvent {
    private Long raceId;
    private String profileId;
    private Integer fuel;
}
