package ru.zotov.carracing.anticheat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 03.09.2021
 * Результат проверки заезда на читерство
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cheat_review", schema = "anticheat_schema")
public class CheatReview {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_id")
    private UUID profileId;
    @Column(name = "external_race_id")
    private UUID externalRaceId;
    @Column(name = "race_start_time")
    private Long raceStartTime;
    @Nullable
    @Column(name = "race_finish_time")
    private Long raceFinishTime;
    @Column(name = "reward_id")
    private Long rewardId;
    @Column(name = "race_valid")
    private Boolean raceValid;
    @Column(name = "description")
    private String description;


}
