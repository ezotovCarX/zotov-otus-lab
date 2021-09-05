package ru.zotov.carracing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 27.04.2021
 * Dto авто
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    /**
     * Внешний ид
     */
    private UUID carId;

    /**
     * Наименование авто
     */
    private String name;

    /**
     * Мощьность
     */
    private Integer power;

    /**
     * Максимальная скорость
     */
    private Integer maxSpeed;
}
