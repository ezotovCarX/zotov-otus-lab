package ru.zotov.carracing.profile.service;

import ru.zotov.carracing.profile.entity.Profile;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 14.09.2021
 */
public interface ProfileService {
    Profile create(UUID externalId);

    Optional<Profile> getCurrentProfile();

    void progressIncrement(UUID externalId, Long score);

    void regressProgress(UUID externalId, Long score);
}
