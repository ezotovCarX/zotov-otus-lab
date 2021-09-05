package ru.zotov.carracing.common.mapper;

import org.modelmapper.ModelMapper;

/**
 * Позволяет настроить конкретное поведение маппера {@link ModelMapper}.
 * Все конфиги маппера должны быть наследниками данного интерфейса
 * Фабрика {@link ModelMapperFactoryBean} собирает конфиг маппера имеено на основе данного интерфейса
 */
public interface ModelMapperConfigurer {

    /**
     * Настойка экземпляра маппера
     *
     * @param modelMapper {@link ModelMapper} текущий инстанс маппера
     */
    void configure(ModelMapper modelMapper);
}
