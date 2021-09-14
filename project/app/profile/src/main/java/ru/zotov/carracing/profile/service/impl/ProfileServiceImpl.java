package ru.zotov.carracing.profile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.profile.entity.Profile;
import ru.zotov.carracing.profile.repo.ProfileRepository;
import ru.zotov.carracing.profile.service.ProfileService;
import ru.zotov.carracing.security.filter.CustomUser;
import ru.zotov.carracing.security.utils.SecurityService;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 14.09.2021
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private static final long BASE_SCORE_CALC = 100L;
    private final ProfileRepository profileRepository;
    private final SecurityService securityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Profile create(UUID externalId) {
        Profile profile = Profile.builder()
                .currentCarId(1)
                .externalId(externalId)
                .level(0)
                .progress(0)
                .score(0L)
                .build();

        return profileRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> getCurrentProfile() {
        return securityService.getUser()
                .map(CustomUser::getId)
                .map(UUID::fromString)
                .flatMap(profileRepository::findByExternalId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void progressIncrement(UUID externalId, Long score) {
        profileRepository.findByExternalId(externalId).ifPresent(p -> {
            p.setScore(p.getScore() + (BASE_SCORE_CALC - score));
            p.setProgress(p.getProgress() + 1);
            profileRepository.save(p);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regressProgress(UUID externalId, Long score) {
        profileRepository.findByExternalId(externalId).ifPresent(p -> {
            p.setScore(p.getScore() - (BASE_SCORE_CALC - score));
            p.setProgress(p.getProgress() - 1);
            profileRepository.save(p);
        });
    }
}
