package ru.zotov.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.enums.RewardType;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 10.08.2021
 * Награда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reward", schema = "wallet_schema")
public class Reward {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RewardType rewardType;
    @Column(name = "name")
    private String name;
    @Column(name = "total")
    private Integer total;
}
