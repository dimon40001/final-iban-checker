package com.df.luminor.iban.mapper;

import com.df.luminor.iban.dao.entity.IbanDBVO;
import com.df.luminor.iban.dto.IbanDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IbanDtoToDbvoMapper {

    IbanDtoToDbvoMapper INSTANCE = Mappers.getMapper(IbanDtoToDbvoMapper.class);

    IbanDBVO dto2dbvo(IbanDTO from);

    @AfterMapping
    default void populate(IbanDTO from, @MappingTarget IbanDBVO to) {
        to.setCountryCode("");
        to.setCheckDigits("");
        to.setBban("");
        var ibanLength = from.getIban().length();

        to.setCountryCode(from.getIban().substring(0, Math.min(ibanLength, 2)));
        if (ibanLength <= 2) {
            return;
        }

        to.setCheckDigits(from.getIban().substring(2, Math.min(ibanLength, 4)));
        if (ibanLength <= 4) {
            return;
        }

        to.setBban(from.getIban().substring(4));
    }

}
