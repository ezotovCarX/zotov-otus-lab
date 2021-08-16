package ru.zotov.carracing.common.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

/**
 * Обертка для создания полноценного конфига конвертера
 */
public abstract class ConverterConfigurerSupport<S, D> implements ModelMapperConfigurer {
    /**
     * Реализация конвертера
     *
     * @param source      объект источник
     * @param modelMapper маппер, для конвертирования вложенных объектов
     * @return целевой сконвертированный объект
     */
    protected abstract D convert(S source, ModelMapper modelMapper);

    /**
     * Общий метод для конфигурирования маппера.
     * Регистрирует в маппере конвертер
     *
     * @param modelMapper инстанс model mapper для конфигурирования
     */
    @Override
    public abstract void configure(ModelMapper modelMapper);

    /**
     * Получить конвертер
     *
     * @param modelMapper маппер
     * @return конвертер
     */
    protected final Converter<S, D> getConverter(ModelMapper modelMapper) {
        return context -> convert(context.getSource(), modelMapper);
    }
}
