package com.df.luminor.iban.mapper;

import com.df.luminor.iban.dao.entity.IbanDBVO;
import com.df.luminor.iban.dto.IbanDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IbanDbvoToDtoMapper {

    IbanDbvoToDtoMapper INSTANCE = Mappers.getMapper(IbanDbvoToDtoMapper.class);

    @Mapping(target = "iban", ignore = true)
    IbanDTO dbvo2dto(IbanDBVO from);

    @AfterMapping
    default void makeIban(IbanDBVO from, @MappingTarget IbanDTO to) {
        to.setIban(from.getCountryCode() + from.getCheckDigits() + from.getBban());
    }

}
