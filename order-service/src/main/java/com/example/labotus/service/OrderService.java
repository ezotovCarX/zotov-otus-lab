package com.example.labotus.service;

import com.example.labotus.domain.Order;
import com.example.labotus.domain.StateOrderEnum;
import com.example.labotus.domain.UpdatedOrderDto;
import com.example.labotus.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
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

    /**
     * Получить заказ по ид
     *
     * @param orderId ид заказа
     * @return заказ
     */
    public Optional<Order> findById(@NonNull Long orderId) {
        return orderRepo.findById(orderId);
    }

    /**
     * Сохранить заказ
     *
     * @param order заказ
     * @return заказ
     */
    public Order save(@NonNull Order order) {

        return orderRepo.save(order);
    }

    /**
     * Редактировать заказ
     *
     * @param orderPair заказ
     * @return сохраненный заказ
     */
    @Transactional(rollbackOn = Exception.class)
    public Order update(@NonNull Pair<Order, Order> orderPair) {

        Order savedOrder = findById(orderPair.getSecond().getId())
                .filter(so -> so.getState().ordinal() == orderPair.getFirst().getState().ordinal() ||
                        so.getState().ordinal() + 1 == orderPair.getFirst().getState().ordinal())
                .filter(o -> o.getState().equals(orderPair.getFirst().getState()))
                .filter(o -> o.getAmount().equals(orderPair.getFirst().getAmount()))
                .orElseThrow();
        savedOrder.setState(orderPair.getSecond().getState());
        savedOrder.setAmount(orderPair.getSecond().getAmount());

        return save(savedOrder);
    }

    /**
     * Удалить заказ
     *
     * @param orderId ид заказа
     */
    public void delete(@NonNull Long orderId) {
        orderRepo.deleteById(orderId);
    }

    /**
     * Поиск заказов по ид пользователя
     *
     * @param userId ид пользователя
     * @return список заказов
     */
    public Collection<Order> findByUserId(@NonNull Long userId) {
        return orderRepo.findAllByUserId(userId);
    }
}
