package ru.zotov.carracing.common.mapper;

import lombok.Setter;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Фабрика. Создает и настраивает маппер, делегируя настройку наследникам ModelMapperConfigurer
 */
@Component
public class ModelMapperFactoryBean implements FactoryBean<ModelMapper> {

    /**
     * Список конфигов маппера
     */
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<ModelMapperConfigurer> configurers;

    /**
     * Создает маппер. Проходит по всем конфигам, передает им текущий инстанц маппера для конфигурирования
     */
    @Override
    @SneakyThrows
    public ModelMapper getObject() {
        final ModelMapper modelMapper = new ModelMapper();

        try {
            if (configurers != null) {
                configurers.forEach(c -> c.configure(modelMapper));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return modelMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return ModelMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
