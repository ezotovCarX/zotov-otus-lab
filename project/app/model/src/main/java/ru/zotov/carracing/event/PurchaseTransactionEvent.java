package ru.zotov.carracing.event;

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
public class PurchaseTransactionEvent {
    private Long id;
    private String profileId;
    private String externalId;
    private String token;
}
