package ru.zotov.auth.service.impl;

import ru.zotov.auth.entity.JwtUser;
import ru.zotov.auth.entity.Player;

import java.util.Collections;
import java.util.Date;


public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(Player user) {
        return new JwtUser(
                user.getId(),
                user.getProfileId().toString(),
                user.getNickname(),
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList(),
                true,
                new Date());
    }

}
