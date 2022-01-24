package com.df.luminor;

import com.df.luminor.iban.dao.entity.IbanDBVO;
import com.df.luminor.iban.dto.IbanDTO;

public class TestHelper {

    private TestHelper() {
    }

    public static String getInvalidIbanAsString() {
        return "XX00BBAN";
    }

    public static String getValidIbanAsString() {
        return "AL47212110090000000235698741";
    }

    public static IbanDBVO getValidIbanDBVO() {
        var ibanDBVO = new IbanDBVO();
        ibanDBVO.setCountryCode("AL");
        ibanDBVO.setCheckDigits("47");
        ibanDBVO.setBban("212110090000000235698741");
        ibanDBVO.setValid(true);
        return ibanDBVO;
    }

    public static IbanDBVO getInvalidIbanDBVO() {
        var ibanDBVO = new IbanDBVO();
        ibanDBVO.setCountryCode("AL");
        ibanDBVO.setCheckDigits("41");
        ibanDBVO.setBban("212110090000000235698741");
        ibanDBVO.setValid(false);
        return ibanDBVO;
    }

    public static IbanDTO getValidDTO() {
        return new IbanDTO(getValidIbanAsString(), true);
    }

    public static IbanDTO getInvalidDTO() {
        return new IbanDTO(getInvalidIbanAsString(), false);
    }
}
