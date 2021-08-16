package ru.zotov.carracing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.enums.RewardType;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 10.08.2021
 * Заезд
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "race", schema = "dictionary_schema")
public class Race {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    private Reward reward;
    @Column(name = "name")
    private String name;
    @Column(name = "track_id")
    private Integer trackId;
}
