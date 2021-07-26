package com.example.labotus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 27.04.2021
 * Заказ
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order", schema = "order_schema")
public class Order {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Состояние заказа
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StateOrderEnum state;

    /**
     * Сумма заказа
     */
    @Column(name = "amount")
    private Float amount;
    /**
     * Ид пользователя
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * Ключ идемпотентности
     */
    @Column(name = "request_id")
    private UUID requestId;
}
