package com.df.luminor.iban.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.df.luminor.TestHelper.getInvalidIbanDBVO;
import static com.df.luminor.TestHelper.getValidIbanDBVO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IbanRepositoryInMemoryITTest {

    @Autowired
    private IbanRepository ibanRepository;

    @Test
    void findAllByValid() {
        var validIban = getValidIbanDBVO();
        var inValidIban = getInvalidIbanDBVO();
        ibanRepository.save(validIban);
        ibanRepository.save(inValidIban);

        var allIbans = ibanRepository.findAll();
        var validIbans = ibanRepository.findAllByValid(true);
        var invalidIbans = ibanRepository.findAllByValid(false);

        assertThat(allIbans).hasSize(2);
        assertThat(validIbans).hasSize(1);
        assertThat(invalidIbans).hasSize(1);
    }

    @Test
    void findByCountryCodeAndCheckDigitsAndBban() {
        var validIban = getValidIbanDBVO();
        ibanRepository.save(validIban);

        var ibanFromDb = ibanRepository.findByCountryCodeAndCheckDigitsAndBban(
                validIban.getCountryCode(), validIban.getCheckDigits(), validIban.getBban()
        );
        var ibans = ibanRepository.findAll();

        assertThat(ibanFromDb.get().getId()).isEqualTo(validIban.getId());
        assertThat(ibans).hasSize(1);
    }

}
