package com.example.carotus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 27.04.2021
 * Класс автомобиля
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car", schema = "car")
public class Car {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование авто
     */
    @Column(name = "name")
    private String name;

    /**
     * Мощьность
     */
    @Column(name = "power")
    private Integer power;

    /**
     * Максимальная скорость
     */
    @Column(name = "max_speed")
    private Integer maxSpeed;
}
