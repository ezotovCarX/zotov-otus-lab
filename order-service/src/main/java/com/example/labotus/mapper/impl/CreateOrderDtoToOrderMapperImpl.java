package com.example.labotus.mapper.impl;

import com.example.labotus.domain.CreateOrderDto;
import com.example.labotus.domain.Order;
import com.example.labotus.domain.StateOrderEnum;
import com.example.labotus.mapper.ConverterConfigurerSupport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author Created by ZotovES on 27.07.2021
 * Маппер из дто заказа в заказ
 */
@Component
public class CreateOrderDtoToOrderMapperImpl extends ConverterConfigurerSupport<CreateOrderDto, Order> {
    /**
     * Реализация конвертера
     *
     * @param source      объект источник
     * @param modelMapper маппер, для конвертирования вложенных объектов
     * @return целевой сконвертированный объект
     */
    @Override
    protected Order convert(CreateOrderDto source, ModelMapper modelMapper) {
        return Order.builder()
                .userId(source.getUserId())
                .state(StateOrderEnum.RECEIVED)
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
        modelMapper.createTypeMap(CreateOrderDto.class, Order.class).setConverter(getConverter(modelMapper));
    }
}
