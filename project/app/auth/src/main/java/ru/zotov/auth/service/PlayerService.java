package ru.zotov.auth.service;

import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.entity.UserTokenInfo;

import java.util.Optional;

/**
 * @author Created by ZotovES on 28.08.2021
 */
public interface PlayerService {
    Player createPlayer(@NonNull Player player);

    Optional<UserTokenInfo> login(@NonNull String email, @NonNull String password);

    Optional<Player> findByEmail(@NonNull String email);

    Optional<UserTokenInfo> refreshToken(@NonNull String refresh);

    Optional<Player> recoveryPassword(@NonNull String email);
}
