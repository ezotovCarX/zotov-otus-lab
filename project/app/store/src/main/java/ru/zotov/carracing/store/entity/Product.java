package ru.zotov.carracing.store.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.store.enums.ProductType;

import javax.annotation.processing.Generated;
import javax.persistence.*;

/**
 * @author Created by ZotovES on 11.09.2021
 * Товар доступный для покупок
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "spr_product", schema = "store_schema")
public class Product {
    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "count")
    private Integer count;
}
