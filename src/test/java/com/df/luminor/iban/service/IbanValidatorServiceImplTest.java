package com.df.luminor.iban.service;

import java.util.Map;

import com.df.luminor.iban.config.CountryConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.df.luminor.TestHelper.getInvalidIbanDBVO;
import static com.df.luminor.TestHelper.getValidIbanDBVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbanValidatorServiceImplTest {

    @Mock
    private CountryConfig countryConfig;

    @InjectMocks
    private IbanValidatorServiceImpl ibanValidatorServiceImpl;

    @BeforeEach
    void beforeEach() {
        var albaniaConfig = new CountryConfig.CountrySpecificIban();
        albaniaConfig.setIbanlength(28);
        albaniaConfig.setPattern("D08A16");
        when(countryConfig.getIbanconfig()).thenReturn(Map.of("AL", albaniaConfig));
    }

    @Test
    void whenValidIban_thenValidated() {
        var valid = ibanValidatorServiceImpl.isValid(getValidIbanDBVO());
        assertThat(valid).isTrue();
    }

    @Test
    void whenInvalidIban_thenNotValidated() {
        var valid = ibanValidatorServiceImpl.isValid(getInvalidIbanDBVO());
        assertThat(valid).isFalse();
    }

}