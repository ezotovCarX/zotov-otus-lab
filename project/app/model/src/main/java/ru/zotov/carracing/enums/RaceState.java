package ru.zotov.carracing.enums;

import lombok.Getter;

/**
 * @author Created by ZotovES on 17.08.2021
 * Статус заезда
 */
@Getter
public enum RaceState {
    LOAD,
    LOAD_FAILED,
    LOADED,
    START,
    FINISH_SUCCESS,
    FINISH_FAILED,
    CANCEL
}
