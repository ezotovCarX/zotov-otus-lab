package ru.zotov.carracing.anticheat.service;

import org.springframework.lang.NonNull;
import ru.zotov.carracing.anticheat.entity.CheatReview;

/**
 * @author Created by ZotovES on 03.09.2021
 */
public interface CheatReviewService {
    void reviewRaceResult(@NonNull CheatReview cheatReview);
}
