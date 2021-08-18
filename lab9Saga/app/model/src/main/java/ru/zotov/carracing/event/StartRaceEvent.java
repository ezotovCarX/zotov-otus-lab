package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 17.08.2021
 * Событие старта заезда
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartRaceEvent {
    private Long raceId;
    private Integer fuel;
}
