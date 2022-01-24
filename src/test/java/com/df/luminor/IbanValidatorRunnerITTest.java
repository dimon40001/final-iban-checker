package com.df.luminor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.df.luminor.iban.rest.Filter;
import com.df.luminor.iban.rest.IbanRestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.df.luminor.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class IbanValidatorRunnerITTest {

    @Autowired
    IbanRestService ibanRestService;

    @Test
    void contextLoads() {
        assertThat(ibanRestService).isNotNull();
    }

    @Test
    void testCreateReadDelete() {
        // GIVEN
        var ibansToCheck = List.of(getValidDTO(), getInvalidDTO());

        // WHEN
        var ibansValidated = ibanRestService.validateIbans(ibansToCheck);

        // THEN
        assertThat(ibansValidated)
                .hasSize(2)
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(ibansToCheck);
    }

    @Test
    void testValidate() {
        // GIVEN
        var actualIbans = List.of(getValidDTO(), getInvalidDTO(), getValidDTO());
        var expectedIbansFromDb = List.of(getValidDTO(), getInvalidDTO());

        // WHEN
        var ibansValidated = ibanRestService.validateIbans(actualIbans);
        var ibansFromDb = ibanRestService.getIbans(Filter.ALL);

        // THEN
        assertThat(ibansValidated)
                .hasSize(3)
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(actualIbans);
        assertThat(ibansFromDb)
                .hasSize(2)
                .usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedIbansFromDb);
    }
}
