package ru.zotov.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.auth.entity.Player;

/**
 * @author Created by ZotovES on 28.08.2021
 */
public interface PlayerRepo extends JpaRepository<Player, Long> {
}
