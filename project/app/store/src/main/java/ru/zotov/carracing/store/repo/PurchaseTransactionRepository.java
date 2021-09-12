package ru.zotov.carracing.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.enums.ValidateState;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction, Long> {
    List<PurchaseTransaction> findByValidateStateIn(Set<ValidateState> validateState);
}
