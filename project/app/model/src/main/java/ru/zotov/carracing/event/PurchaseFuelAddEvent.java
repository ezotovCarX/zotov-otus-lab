package ru.zotov.carracing.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 12.09.2021
 * Событие добавления топлива
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseFuelAddEvent {
    String profileId;
    Integer fuel;
}
