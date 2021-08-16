package ru.zotov.carracing.common.mapper;

import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Обертка для ModelMapper выполняет стандартные преобразования
 */
@Component
public class Mapper {

    @Setter(onMethod = @__({@Autowired}))
    private ModelMapper instance;

    /**
     * Одиночное преоборазование по классу
     */
    public <S, T> T map(S source, Class<T> targetClass) {
        return instance.map(source, targetClass);
    }

    /**
     * Преобразование Списка
     */
    public <S, T> Collection<T> mapList(Collection<S> source, Class<T> targetClass) {
        return source.stream().map(t -> instance.map(t, targetClass)).collect(Collectors.toList());
    }

    /**
     * Преобразование Списка
     */
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream().map(t -> instance.map(t, targetClass)).collect(Collectors.toList());
    }

}
