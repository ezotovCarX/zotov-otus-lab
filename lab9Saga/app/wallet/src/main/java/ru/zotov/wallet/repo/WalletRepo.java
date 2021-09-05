package ru.zotov.wallet.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.wallet.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 23.08.2021
 */
@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByProfileId(UUID profileId);
}
