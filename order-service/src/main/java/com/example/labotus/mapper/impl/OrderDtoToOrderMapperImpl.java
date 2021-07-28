package com.example.labotus.mapper.impl;

import com.example.labotus.domain.Order;
import com.example.labotus.domain.OrderDto;
import com.example.labotus.domain.UpdatedOrderDto;
import com.example.labotus.mapper.ConverterConfigurerSupport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Created by ZotovES on 27.07.2021
 * Маппер дто обновлениея заказа в заказ
 */
@Component
public class OrderDtoToOrderMapperImpl extends ConverterConfigurerSupport<OrderDto, Order> {
    /**
     * Реализация конвертера
     *
     * @param source      объект источник
     * @param modelMapper маппер, для конвертирования вложенных объектов
     * @return целевой сконвертированный объект
     */
    @Override
    protected Order convert(OrderDto source, ModelMapper modelMapper) {
        return Order.builder()
                .id(source.getId())
                .state(source.getState())
                .amount(source.getAmount())
                .build();
    }

    /**
     * Общий метод для конфигурирования маппера.
     * Регистрирует в маппере конвертер
     *
     * @param modelMapper инстанс model mapper для конфигурирования
     */
    @Override
    public void configure(ModelMapper modelMapper) {
        modelMapper.createTypeMap(OrderDto.class, Order.class).setConverter(getConverter(modelMapper));
    }
}
