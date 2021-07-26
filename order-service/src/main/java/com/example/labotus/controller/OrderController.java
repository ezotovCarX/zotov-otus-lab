package com.example.labotus.controller;

import com.example.labotus.domain.Order;
import com.example.labotus.domain.OrderDto;
import com.example.labotus.domain.UpdatedOrderDto;
import com.example.labotus.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        return orderService.findById(orderId)
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
    public ResponseEntity<OrderDto> updateByOrderId(@PathVariable("orderId") Long orderId,
            @RequestBody UpdatedOrderDto orderDto) {
        validateOrderDto(orderId, orderDto);

        return Optional.of(mappingToOrder(orderDto))
                .map(orderService::update)
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
        orderService.delete(orderId);

        return ResponseEntity.ok().build();
    }

    /**
     * Получить все заказы пользователя
     *
     * @return dto авто
     */
    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@RequestParam Long userId) {
        List<OrderDto> ordersDto = orderService.findByUserId(userId).stream()
                .map(c -> mapper.map(c, OrderDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ordersDto);
    }

    /**
     * Валидация дто заказа
     *
     * @param orderId  ид заказа
     * @param orderDto дто заказа
     */
    private void validateOrderDto(Long orderId, UpdatedOrderDto orderDto) {
        if (orderDto == null || !orderDto.getId().equals(orderId)) {
            throw new IllegalArgumentException();
        }
        orderService.findById(orderId)
                .filter(o -> o.getState().equals(orderDto.getState()))
                .filter(o -> o.getAmount().equals(orderDto.getAmount()))
                .orElseThrow(IllegalStateException::new);
    }

    private Order mappingToOrder(UpdatedOrderDto orderDto) {
        return Order.builder()
                .state(orderDto.getNewState())
                .amount(orderDto.getNewAmount())
                .build();
    }
}
