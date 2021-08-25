package ru.zotov.wallet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.carracing.entity.Wallet;

import java.util.Optional;

/**
 * @author Created by ZotovES on 23.08.2021
 */
public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByProfileId(Long profileId);
}
