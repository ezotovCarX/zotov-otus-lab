package ru.zotov.auth.service;

import org.springframework.lang.NonNull;
import ru.zotov.auth.entity.Player;

/**
 * @author Created by ZotovES on 28.08.2021
 */
public interface PlayerService {
    Player createPlayer(@NonNull Player player);
}
