package ru.zotov.carracing.profile.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.carracing.profile.entity.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByExternalId(UUID externalId);
}
