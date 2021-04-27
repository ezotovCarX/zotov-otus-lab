package com.example.carotus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
