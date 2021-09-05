package ru.zotov.carracing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 03.09.2021
 * Дто кошлька игрока
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private Long id;
    private UUID profileId;
    private Integer fuel;
    private Integer money;
}
