package ru.zotov.carracing.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.store.enums.ValidateState;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Created by ZotovES on 11.09.2021
 * Покупка
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase", schema = "store_schema")
public class PurchaseTransaction {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "count")
    private Integer count;

    @Column(name = "purchase_date")
    private ZonedDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "validate_state")
    private ValidateState validateState;

    @Column(name = "token")
    private String token;
}
