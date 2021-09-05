package ru.zotov.carracing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.enums.RaceState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 18.08.2021
 */
public interface RaceRepo extends JpaRepository<Race, Long> {
    List<Race> findByProfileIdAndStateIn(UUID profileId, List<RaceState> states);

    Optional<Race> findByExternalId(UUID externalID);
}
