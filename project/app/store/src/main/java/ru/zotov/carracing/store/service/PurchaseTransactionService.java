package ru.zotov.carracing.store.service;

import org.springframework.lang.NonNull;
import ru.zotov.carracing.event.PurchaseTransactionEvent;
import ru.zotov.carracing.store.entity.PurchaseTransaction;

import java.util.List;

/**
 * @author Created by ZotovES on 11.09.2021
 */
public interface PurchaseTransactionService {
    void createPurchaseTransaction(@NonNull String externalId, @NonNull String token);

    List<PurchaseTransaction> findAllNotCheckedPurchaseTransactions();

    void checkValidPayment(@NonNull PurchaseTransactionEvent purchaseTransactionEvent);
}
