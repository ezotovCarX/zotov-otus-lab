package com.example.labotus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 27.04.2021
 * Dto для редактирования заказа
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedOrderDto {
    private Long id;
    /**
     * Состояние заказа
     */
    private StateOrderEnum state;
    /**
     * Сумма заказа
     */
    private Float amount;
    /**
     * Новое Состояние заказа
     */
    private StateOrderEnum newState;
    /**
     * Новая Сумма заказа
     */
    private Float newAmount;
}
