package ru.zotov.wallet.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.common.mapper.ConverterConfigurerSupport;
import ru.zotov.carracing.dto.WalletDto;
import ru.zotov.wallet.entity.Wallet;

/**
 * @author Created by ZotovES on 03.09.2021
 */
@Component
public class WalletToWalletDtoMapperImpl extends ConverterConfigurerSupport<Wallet, WalletDto> {
    /**
     * Реализация конвертера
     *
     * @param source      объект источник
     * @param modelMapper маппер, для конвертирования вложенных объектов
     * @return целевой сконвертированный объект
     */
    @Override
    protected WalletDto convert(Wallet source, ModelMapper modelMapper) {
        return WalletDto.builder()
                .id(source.getId())
                .profileId(source.getProfileId())
                .fuel(source.getFuel())
                .money(source.getMoney())
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
        modelMapper.createTypeMap(Wallet.class, WalletDto.class).setConverter(getConverter(modelMapper));
    }
}
