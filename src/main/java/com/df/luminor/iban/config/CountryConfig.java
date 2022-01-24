package com.df.luminor.iban.config;

import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "config", ignoreInvalidFields = true)
@Getter
@Setter
@Validated
public class CountryConfig {

    private Map<String, CountrySpecificIban> ibanconfig;

    @Getter
    @Setter
    public static class CountrySpecificIban {
        @Min(5)
        @Max(34)
        private int ibanlength;
        private String pattern;
    }

}
