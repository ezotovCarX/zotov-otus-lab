package ru.zotov.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 08.09.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String refreshToken;
    private String token;
}
