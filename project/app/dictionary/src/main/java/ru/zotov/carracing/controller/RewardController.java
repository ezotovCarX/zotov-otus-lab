package ru.zotov.carracing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.entity.Reward;
import ru.zotov.carracing.repo.RewardRepo;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 10.08.2021
 * Контроллер наград
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "rewards", produces = APPLICATION_JSON_VALUE)
public class RewardController {
    private final RewardRepo rewardRepo;

    /**
     * Создать награду
     *
     * @param reward награда
     * @return награда
     */
    @PostMapping
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        return Optional.ofNullable(reward)
                .map(rewardRepo::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить награду по ид
     *
     * @param rewardId ид награды
     * @return награда
     */
    @GetMapping("{rewardId}")
    public ResponseEntity<Reward> getById(@PathVariable("rewardId") Long rewardId) {
        return rewardRepo.findById(rewardId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить все награды
     *
     * @return награда
     */
    @GetMapping
    public ResponseEntity<List<Reward>> getAllRewards() {
        return ResponseEntity.ok(rewardRepo.findAll());
    }

    /**
     * Редактировать награду по ид
     *
     * @param rewardId ид награды
     * @return награда
     */
    @PutMapping("{rewardId}")
    public ResponseEntity<Reward> updateReward(@PathVariable("rewardId") Long rewardId, @RequestBody Reward reward) {
        return rewardRepo.findById(rewardId)
                .map(updateFieldReward(reward))
                .map(rewardRepo::save)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Удалить награду по ид
     *
     * @param rewardId ид награды
     */
    @DeleteMapping("{rewardId}")
    public ResponseEntity<Void> deleteById(@PathVariable("rewardId") Long rewardId) {
        rewardRepo.deleteById(rewardId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновление полей награды
     *
     * @param incomeReward дто награды
     */
    private Function<Reward, Reward> updateFieldReward(Reward incomeReward) {
        return reward -> {
            reward.setName(incomeReward.getName());
            reward.setRewardType(incomeReward.getRewardType());
            reward.setTotal(incomeReward.getTotal());

            return reward;
        };
    }
}
