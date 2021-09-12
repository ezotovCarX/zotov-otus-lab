package ru.zotov.carracing.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 11.09.2021
 * Дто для отправки результата проверки покупки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePaymentCheckedDto {
    private String id;
    private String token;
}
