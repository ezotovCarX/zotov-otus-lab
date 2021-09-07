package ru.zotov.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.zotov.auth.entity.Player;

import java.util.Optional;

/**
 * @author Created by ZotovES on 28.08.2021
 */
public interface PlayerRepo extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(@NonNull String email);
}
