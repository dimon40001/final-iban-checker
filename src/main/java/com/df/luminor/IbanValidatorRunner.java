package com.df.luminor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class IbanValidatorRunner {

    public static void main(String[] args) {
        SpringApplication.run(IbanValidatorRunner.class, args);
    }

}
