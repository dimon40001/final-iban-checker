package com.df.luminor.iban.mapper;

import com.df.luminor.iban.dto.IbanDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IbanDtoToDbvoMapperTest {

    private IbanDtoToDbvoMapper mapper = IbanDtoToDbvoMapper.INSTANCE;

    @Test
    void test_valid_iban_is_parsed() {
        // GIVEN
        var from = new IbanDTO();
        var countryCode = "AL";
        var checkDigits = "47";
        var bban = "212110090000000235698741";
        from.setIban(countryCode + checkDigits + bban);

        // WHEN
        var to = mapper.dto2dbvo(from);

        // THEN
        assertThat(to.getCountryCode()).isEqualTo(countryCode);
        assertThat(to.getCheckDigits()).isEqualTo(checkDigits);
        assertThat(to.getBban()).isEqualTo(bban);
    }

    @Test
    void test_1_character_iban_is_parsed() {
        // GIVEN
        var from = new IbanDTO();
        var countryCode = "A";
        var checkDigits = "";
        var bban = "";
        from.setIban(countryCode + checkDigits + bban);

        // WHEN
        var to = mapper.dto2dbvo(from);

        // THEN
        assertThat(to.getCountryCode()).isEqualTo(countryCode);
        assertThat(to.getCheckDigits()).isEqualTo(checkDigits);
        assertThat(to.getBban()).isEqualTo(bban);
    }

}