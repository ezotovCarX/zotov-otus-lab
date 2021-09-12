package ru.zotov.carracing.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.store.dto.RequestPaymentCheckedDto;
import ru.zotov.carracing.store.dto.ResponsePaymentCheckedDto;
import ru.zotov.carracing.store.entity.PurchaseTransaction;
import ru.zotov.carracing.store.service.PurchaseTransactionService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 11.09.2021
 * Контроллер для совершения покупок
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "payments", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class CheckPayController {
    private final Mapper mapper;
    private final PurchaseTransactionService purchaseTransactionService;

    /**
     * Создать покупку
     *
     * @return кошелек игрока
     */
    @PostMapping
    public ResponseEntity<ResponsePaymentCheckedDto> createPurchase(@RequestBody RequestPaymentCheckedDto paymentCheckedDto) {
        purchaseTransactionService.createPurchaseTransaction(paymentCheckedDto.getId(), paymentCheckedDto.getToken());

        ResponsePaymentCheckedDto responsePaymentCheckedDto = ResponsePaymentCheckedDto.builder()
                .id(paymentCheckedDto.getId())
                .token(paymentCheckedDto.getToken())
                .build();

        return ResponseEntity.ok(responsePaymentCheckedDto);
    }

    /**
     * Переключить возвращаемый результат проверки
     */
    @GetMapping("/notChecked")
    public ResponseEntity<List<ResponsePaymentCheckedDto>> getAllNotCheckedPurchaseTransactions() {
        List<ResponsePaymentCheckedDto> purchaseTransactionList =
                mapper.mapList(purchaseTransactionService.findAllNotCheckedPurchaseTransactions(),
                        ResponsePaymentCheckedDto.class);

        return ResponseEntity.ok(purchaseTransactionList);
    }
}
