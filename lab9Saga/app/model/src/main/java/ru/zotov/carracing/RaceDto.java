package ru.zotov.carracing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 10.08.2021
 * Дто заезда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceDto {
    private Long id;
    private Long rewardId;
    private String name;
    private Integer trackId;
}
