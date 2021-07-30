package com.example.labotus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 27.04.2021
 * Dto для создания заказа
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    /**
     * Ид пользователя
     */
    private Long userId;

    /**
     * Сумма заказа
     */
    private Float amount;
}
