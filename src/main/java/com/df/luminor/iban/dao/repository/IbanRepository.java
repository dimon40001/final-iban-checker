package com.df.luminor.iban.dao.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.df.luminor.iban.dao.entity.IbanDBVO;

@Repository
public interface IbanRepository extends PagingAndSortingRepository<IbanDBVO, Long> {

    Iterable<IbanDBVO> findAllByValid(boolean valid);

    Optional<IbanDBVO> findByCountryCodeAndCheckDigitsAndBban(String countryCode, String checkDigits, String bban);

}
