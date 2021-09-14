package ru.zotov.carracing.anticheat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.anticheat.entity.CheatReview;
import ru.zotov.carracing.anticheat.repo.CheatReviewRepo;
import ru.zotov.carracing.anticheat.service.CheatReviewService;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.RaceFinishEvent;
import ru.zotov.carracing.event.RewardEvent;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Created by ZotovES on 03.09.2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheatReviewServiceImpl implements CheatReviewService {
    private static final int MIN_TIME_RACE = 60;
    private final CheatReviewRepo cheatReviewRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewRaceResult(@NonNull CheatReview cheatReview) {
        Optional.of(cheatReview)
                .filter(c -> Objects.nonNull(c.getRaceFinishTime()))
                .filter(c -> (c.getRaceFinishTime() - c.getRaceStartTime()) < MIN_TIME_RACE)
                .ifPresentOrElse(c -> {
                    c.setRaceValid(false);
                    c.setDescription("Игрок проехал заезд быстрее минуты");
                    log.info("Античит проверка не пройдена");
                    kafkaTemplate.send(Constants.KAFKA_RETURN_REWARD_TOPIC, buildRewardEvent(c));
                    kafkaTemplate.send(Constants.KAFKA_FAIL_CHEAT_REVIEW_TOPIC, c.getExternalRaceId());
                    kafkaTemplate.send(Constants.PROFILE_REGRESS, buildRaceFinishEvent(c));
                }, () -> log.info("Античит проверка пройдена успешно"));

        cheatReviewRepo.save(cheatReview);
    }

    private RaceFinishEvent buildRaceFinishEvent(CheatReview c) {
        return RaceFinishEvent.builder()
                .startTime(c.getRaceStartTime())
                .finishTime(c.getRaceFinishTime())
                .profileId(c.getProfileId().toString())
                .build();
    }

    private RewardEvent buildRewardEvent(CheatReview c) {
        return RewardEvent.builder().rewardId(c.getRewardId()).profileId(c.getProfileId().toString()).build();
    }
}
