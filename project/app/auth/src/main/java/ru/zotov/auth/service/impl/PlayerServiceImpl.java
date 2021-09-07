package ru.zotov.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.repo.PlayerRepo;
import ru.zotov.auth.service.PlayerService;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.event.CreatePlayerEvent;
import ru.zotov.carracing.event.SendMailEvent;

import java.util.Optional;
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
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Player createPlayer(@NonNull Player player) {
        playerRepo.findByEmail(player.getEmail().toLowerCase())
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Email is busy");
                });

        player.setProfileId(UUID.randomUUID());
        String rawPassword = generateAuthCode();
        player.setPassword(passwordEncoder.encode(rawPassword));
        player.setEmail(player.getEmail().toLowerCase());

        kafkaTemplate.send(Constants.KAFKA_SEND_MAIL_TOPIC, getMessage(player));
        kafkaTemplate.send(Constants.KAFKA_PLAYER_TOPIC, mapper.map(player, CreatePlayerEvent.class));
        return playerRepo.save(player);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> login(@NonNull String email, @NonNull String password) {
        return playerRepo.findByEmail(email.toLowerCase())
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtTokenProvider.createToken(user.getNickname(), user.getEmail(), user.getPassword()));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Player> findByEmail(@NonNull String email) {
        return playerRepo.findByEmail(email);
    }

    private SendMailEvent getMessage(Player player) {
        return SendMailEvent.builder()
                .email(player.getEmail())
                .messageText("Your are register code " + player.getPassword())
                .build();
    }

    public String generateAuthCode() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            str.append((int) (Math.random() * 10));
        }
        return str.toString();
    }
}
