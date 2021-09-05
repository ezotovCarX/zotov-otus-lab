package ru.zotov.carracing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 10.08.2021
 * Дто заезда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceTemplateDto {
    private Long id;
    private Long rewardId;
    private String name;
    private Integer trackId;
}
