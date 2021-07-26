package com.example.labotus.controller;

import com.example.labotus.domain.Order;
import com.example.labotus.domain.OrderDto;
import com.example.labotus.domain.StateOrderEnum;
import com.example.labotus.repo.OrderRepo;
import com.example.labotus.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 27.04.2021
 * Контроллер управления заказами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "orders", produces = APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderRepo orderRepo;
    private final OrderService orderService;
    private final ModelMapper mapper = new ModelMapper();

    /**
     * Создать заказ
     *
     * @param orderDto дто заказа
     * @return dto заказа
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, @RequestHeader UUID requestId) {
        return Optional.ofNullable(orderDto)
                .map(c -> mapper.map(c, Order.class))
                .map(o -> orderService.create(o, requestId))
                .map(c -> mapper.map(c, OrderDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить заказ по ид
     *
     * @param orderId ид заказ
     * @return dto заказ
     */
    @GetMapping("{orderId}")
    public ResponseEntity<OrderDto> getByOrderId(@PathVariable("orderId") Long orderId) {
        return orderRepo.findById(orderId)
                .map(c -> mapper.map(c, OrderDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Рeдактировать заказ по ид
     *
     * @param orderId  ид заказа
     * @param orderDto dto заказа
     * @return dto заказ
     */
    @PutMapping("{orderId}")
    public ResponseEntity<OrderDto> updateByOrderId(@PathVariable("orderId") Long orderId, @RequestBody OrderDto orderDto) {
        if (validateOrderDto(orderId, orderDto)) {
            return ResponseEntity.badRequest().build();
        }

        return orderRepo.findById(orderId)
                .map(updateFieldOrder(orderDto))
                .map(orderRepo::save)
                .map(c -> mapper.map(c, OrderDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Рeдактировать заказ по ид
     *
     * @param orderId  ид заказа
     * @param orderDto dto заказа
     * @return dto заказ
     */
    @PutMapping("{orderId}/{state}")
    public ResponseEntity<OrderDto> updateByOrderId(@PathVariable("orderId") Long orderId,
            @PathVariable("state") StateOrderEnum state, @RequestBody OrderDto orderDto) {
        if (validateOrderDto(orderId, orderDto)) {
            return ResponseEntity.badRequest().build();
        }

        return orderRepo.findById(orderId)
                .map(updateFieldOrder(orderDto))
                .map(orderRepo::save)
                .map(c -> mapper.map(c, OrderDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удалить заказ по ид
     *
     * @param orderId ид заказ
     * @return dto заказ
     */
    @DeleteMapping("{orderId}")
    public ResponseEntity<Void> orderById(@PathVariable("orderId") Long orderId) {
        orderRepo.findById(orderId).ifPresent(orderRepo::delete);

        return ResponseEntity.ok().build();
    }

    /**
     * Получить все заказы пользователя
     *
     * @return dto авто
     */
    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@RequestParam Long userId) {
        List<OrderDto> ordersDto = orderRepo.findAll().stream()
                .map(c -> mapper.map(c, OrderDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ordersDto);
    }

    /**
     * Обновление суммы заказа
     *
     * @param orderDto дто авто
     */
    private Function<Order, Order> updateFieldOrder(OrderDto orderDto) {
        return order -> {
            order.setAmount(orderDto.getAmount());

            return order;
        };
    }

    /**
     * Валидация дто заказа
     *
     * @param orderId  ид заказа
     * @param orderDto дто заказа
     * @return результат проверки
     */
    private boolean validateOrderDto(Long orderId, OrderDto orderDto) {
        return orderDto == null || !orderDto.getId().equals(orderId);
    }
}
