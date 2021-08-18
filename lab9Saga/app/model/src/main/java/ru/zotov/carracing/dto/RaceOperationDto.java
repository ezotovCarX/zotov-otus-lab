package ru.zotov.carracing.dto;

import ru.zotov.carracing.enums.RaceState;

import java.util.UUID;

/**
 * @author Created by ZotovES on 17.08.2021
 */
public class RaceOperationDto {
    private Long id;
    private UUID externalId;
    private RaceState state;
}
