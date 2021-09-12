package ru.zotov.carracing.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 12.09.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePaymentCheckedIntegrationDto {
    private String id;
    private String token;
    private Boolean resultChecked;
}
