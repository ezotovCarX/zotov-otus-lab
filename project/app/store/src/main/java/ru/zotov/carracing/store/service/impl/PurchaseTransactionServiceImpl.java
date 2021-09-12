package ru.zotov.carracing.store.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.event.PurchaseFuelAddEvent;
import ru.zotov.carracing.event.PurchaseTransactionEvent;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.carracing.store.dto.RequestPaymentCheckedDto;
import ru.zotov.carracing.store.dto.ResponsePaymentCheckedIntegrationDto;
import ru.zotov.carracing.store.entity.Product;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.enums.ValidateState;
import ru.zotov.carracing.store.integration.GoogleStoreIntegration;
import ru.zotov.carracing.store.repo.ProductRepository;
import ru.zotov.carracing.store.repo.PurchaseTransactionRepository;
import ru.zotov.carracing.store.service.PurchaseTransactionService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static ru.zotov.carracing.common.constant.Constants.PURCHASE_FUEL_ADD;
import static ru.zotov.carracing.common.constant.Constants.PURCHASE_TRANSACTION_VALIDATE;

/**
 * @author Created by ZotovES on 11.09.2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {
    private final GoogleStoreIntegration googleStoreIntegration;
    private final PurchaseTransactionRepository purchaseTransactionRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<PurchaseTransaction, Object> kafkaTemplate;
    private final Mapper mapper;
    private final SecurityService securityService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPurchaseTransaction(@NonNull String externalId, @NonNull String token) {
        Optional<Product> product = productRepository.findByExternalId(externalId);
        PurchaseTransaction purchaseTransaction = product
                .map(buildProductPurchaseTransactionFunction(token))
                .orElseThrow();

        PurchaseTransaction savedPurchase = purchaseTransactionRepository.save(purchaseTransaction);

        String profileId = securityService.getUserTh().getId();
        PurchaseTransactionEvent purchaseTransactionEvent = PurchaseTransactionEvent.builder()
                .id(savedPurchase.getId())
                .externalId(externalId)
                .token(token)
                .profileId(profileId)
                .build();

        kafkaTemplate.send(PURCHASE_TRANSACTION_VALIDATE, purchaseTransactionEvent);

        PurchaseFuelAddEvent fuelAddEvent = PurchaseFuelAddEvent.builder()
                .fuel(product.map(Product::getCount).orElseThrow())
                .profileId(profileId).build();

        kafkaTemplate.send(PURCHASE_FUEL_ADD, fuelAddEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseTransaction> findAllNotCheckedPurchaseTransactions() {
        return purchaseTransactionRepository.findByValidateStateIn(Set.of(ValidateState.ERROR_TRY, ValidateState.WAITING));
    }

    @Override
    @Retryable(value = FeignException.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    @Transactional(rollbackFor = Exception.class)
    public void checkValidPayment(@NonNull PurchaseTransactionEvent purchaseTransactionEvent) {
        log.info("Попытка проверить покупку {} в GoogleStore", purchaseTransactionEvent.getExternalId());
        RequestPaymentCheckedDto requestPaymentCheckedDto = RequestPaymentCheckedDto.builder()
                .id(purchaseTransactionEvent.getExternalId())
                .token(purchaseTransactionEvent.getToken())
                .build();
        ResponsePaymentCheckedIntegrationDto resultDto = googleStoreIntegration.checkPayment(requestPaymentCheckedDto);
        Optional<PurchaseTransaction> purchaseTransaction =
                purchaseTransactionRepository.findById(purchaseTransactionEvent.getId());
        purchaseTransaction.ifPresent(purchase -> {
            purchase.setValidateState(resultDto.getResultChecked() ? ValidateState.VALIDATE : ValidateState.INVALIDATE);
            purchaseTransactionRepository.save(purchase);
            if (!resultDto.getResultChecked()) {
                log.info("Проверка покупки прошла не успешно. Списываем топливо");
                PurchaseFuelAddEvent fuelAddEvent = PurchaseFuelAddEvent.builder()
                        .fuel(-purchase.getProduct().getCount())
                        .profileId(purchaseTransactionEvent.getProfileId())
                        .build();

                kafkaTemplate.send(PURCHASE_FUEL_ADD, fuelAddEvent);
            }
        });

        purchaseTransaction.filter(p -> ValidateState.WAITING.equals(p.getValidateState())).ifPresent(p -> {
            PurchaseTransactionEvent returnPurchaseTransactionEvent = PurchaseTransactionEvent.builder()
                    .id(p.getId())
                    .externalId(p.getProduct().getExternalId())
                    .token(p.getToken())
                    .build();

            kafkaTemplate.send(PURCHASE_TRANSACTION_VALIDATE, returnPurchaseTransactionEvent);
        });
    }

    private Function<Product, PurchaseTransaction> buildProductPurchaseTransactionFunction(String token) {
        return product -> PurchaseTransaction.builder()
                .token(token)
                .profileId(securityService.getUserTh().getId())
                .count(product.getCount())
                .purchaseDate(ZonedDateTime.now())
                .product(product)
                .validateState(ValidateState.WAITING)
                .build();
    }
}
