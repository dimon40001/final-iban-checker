package com.df.luminor.iban.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.df.luminor.TestHelper.getValidIbanDBVO;
import static org.assertj.core.api.Assertions.assertThat;

class IbanDbvoToDtoMapperTest {

    private IbanDbvoToDtoMapper mapper = Mappers.getMapper(IbanDbvoToDtoMapper.class);

    @Test
    void test_valid_iban_is_parsed_to_string_representation() {
        // GIVEN
        var from = getValidIbanDBVO();

        // WHEN
        var to = mapper.dbvo2dto(from);

        // THEN
        assertThat(to.getIban()).isEqualTo(from.getCountryCode() + from.getCheckDigits() + from.getBban());
        assertThat(to.isValid()).isEqualTo(from.isValid());
    }

}