package ru.zotov.carracing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 27.04.2021
 * Класс автомобиля
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car", schema = "dictionary_schema")
public class Car {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Внешний ид
     */
    @Column(name = "car_id")
    private UUID carId;

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
