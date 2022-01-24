package com.df.luminor.iban.service;

import com.df.luminor.iban.dao.entity.IbanDBVO;

public interface IbanValidatorService {

    boolean isValid(IbanDBVO iban);
}
