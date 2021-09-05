package ru.zotov.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.repo.PlayerRepo;
import ru.zotov.auth.service.PlayerService;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.event.CreatePlayerEvent;

import java.util.UUID;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepo playerRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Player createPlayer(@NonNull Player player) {
        player.setProfileId(UUID.randomUUID());

        kafkaTemplate.send(Constants.KAFKA_PLAYER_TOPIC, mapper.map(player, CreatePlayerEvent.class));

        return playerRepo.save(player);
    }
}
