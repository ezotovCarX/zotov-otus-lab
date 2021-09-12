package ru.zotov.carracing.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 11.09.2021
 * Входящяя дто для проверки покупки
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentCheckedDto {
    private String id;
    private String token;
}
