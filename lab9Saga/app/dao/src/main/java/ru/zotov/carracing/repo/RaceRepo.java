package ru.zotov.carracing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.carracing.entity.Race;

/**
 * @author Created by ZotovES on 10.08.2021
 */
public interface RaceRepo extends JpaRepository<Race, Long> {
}
