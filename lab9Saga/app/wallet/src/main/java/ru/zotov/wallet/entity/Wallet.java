package ru.zotov.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 23.08.2021
 * Кошелек игрока
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet", schema = "wallet_schema")
public class Wallet {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_id")
    private UUID profileId;
    @Column(name = "fuel")
    private Integer fuel;
    @Column(name = "money")
    private Integer money;

}
