package com.example.labotus.repo;

import com.example.labotus.domain.Order;
import com.example.labotus.domain.StateOrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 27.04.
 * Репозиторий заказов
 */
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    /**
     * Поиск заказа по внешнему id
     *
     * @param requestId внешний ид заказа
     * @return заказ
     */
    Optional<Order> findByRequestId(@NonNull UUID requestId);

    /**
     * Поиск заказов по ид пользователя и статусу заказа
     *
     * @param userId ид пользователя
     * @param state  состояние заказа
     * @return список заказов
     */
    List<Order> findAllByUserIdAndState(@NonNull Long userId, @NonNull StateOrderEnum state);
}
