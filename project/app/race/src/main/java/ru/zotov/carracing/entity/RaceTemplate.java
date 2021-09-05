package ru.zotov.carracing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 10.08.2021
 * Справочник шаблонов заездов
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "race_template", schema = "race_schema")
public class RaceTemplate {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reward_id")
    private Long rewardId;
    @Column(name = "name")
    private String name;
    @Column(name = "track_length")
    private Integer trackLength;
    @Column(name = "fuel_consume")
    private Integer fuelConsume;
    @Column(name = "track_id")
    private Integer trackId;
    @Column(name = "check_on_cheat")
    private Boolean checkOnCheat;
}
