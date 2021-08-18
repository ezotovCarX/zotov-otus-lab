package ru.zotov.carracing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Created by ZotovES on 17.08.2021
 * Дто финишироавнного заезда
 */
@Data
public class RaceFinishDto {
    private Long id;
    private String externalId;
}
