package ru.zotov.carracing.anticheat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.carracing.anticheat.entity.CheatReview;

/**
 * @author Created by ZotovES on 03.09.2021
 */
@Repository
public interface CheatReviewRepo extends JpaRepository<CheatReview, Long> {

}
