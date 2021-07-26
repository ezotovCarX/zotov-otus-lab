package com.example.labotus.service;

import com.example.labotus.domain.Order;
import com.example.labotus.domain.StateOrderEnum;
import com.example.labotus.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Created by ZotovES on 25.07.2021
 * Сервис управления заказами
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    /**
     * Создать заказ
     *
     * @param order заказ
     * @return заказ
     */
    public Order create(@NonNull Order order, UUID requestId) {
        orderRepo.findByRequestId(requestId)
                .filter(o -> !o.getState().equals(StateOrderEnum.COMPLETE))
                .ifPresent(o -> {
                    throw new IllegalArgumentException();
                });
        if (!orderRepo.findAllByUserIdAndState(order.getUserId(), StateOrderEnum.RECEIVED).isEmpty()) {
            throw new IllegalStateException();
        }

        order.setRequestId(requestId);

        return orderRepo.save(order);
    }
}
