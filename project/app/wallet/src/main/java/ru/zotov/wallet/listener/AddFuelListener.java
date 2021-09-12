package ru.zotov.wallet.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.PurchaseFuelAddEvent;
import ru.zotov.wallet.service.WalletService;

import java.util.UUID;

/**
 * @author Created by ZotovES on 12.09.2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AddFuelListener {
    private final WalletService walletService;

    @KafkaListener(topics = Constants.PURCHASE_FUEL_ADD, groupId = Constants.KAFKA_GROUP_ID)
    public void onProcessMessage(PurchaseFuelAddEvent purchaseFuelAddEvent) {
        log.info("Поступило сообщение добавить топлива {} ", purchaseFuelAddEvent.getProfileId());
        walletService.addFuel(UUID.fromString(purchaseFuelAddEvent.getProfileId()), purchaseFuelAddEvent.getFuel());
    }
}
