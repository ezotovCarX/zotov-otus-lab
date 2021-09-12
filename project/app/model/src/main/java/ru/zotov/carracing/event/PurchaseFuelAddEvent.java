package ru.zotov.carracing.event;

import lombok.Builder;
import lombok.Data;

/**
 * @author Created by ZotovES on 12.09.2021
 * Событие добавления топлива
 */
@Data
@Builder
public class PurchaseFuelAddEvent {
    String profileId;
    Integer fuel;
}
