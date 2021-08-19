package ru.zotov.carracing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.enums.RaceState;

import java.util.UUID;

/**
 * @author Created by ZotovES on 17.08.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceOperationDto {
    private Long id;
    private UUID externalId;
    private RaceState state;
}
