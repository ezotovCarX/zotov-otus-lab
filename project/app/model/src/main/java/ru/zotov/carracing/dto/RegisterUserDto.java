package ru.zotov.carracing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterUserDto {
    private String nickname;
    private UUID profileId;
    private String email;
}
