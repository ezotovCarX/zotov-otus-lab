package ru.zotov.carracing.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 14.09.2021
 * Профиль игрока
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile", schema = "profile_schema")
public class Profile {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private UUID externalId;
    @Column(name = "progress")
    private Integer progress;
    @Column(name = "current_car_id")
    private Integer currentCarId;
    @Column(name = "score")
    private Long score;
    @Column(name = "level")
    private Integer level;
}
