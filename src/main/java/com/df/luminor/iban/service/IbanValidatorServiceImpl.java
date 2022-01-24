package com.df.luminor.iban.service;

import java.math.BigInteger;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.luminor.iban.config.CountryConfig;
import com.df.luminor.iban.dao.entity.IbanDBVO;

@Component
public class IbanValidatorServiceImpl implements IbanValidatorService {

    private static final String ONLY_DIGITS = "[0-9]{$1}";
    private static final String ONLY_CAPITALS_A_Z = "[A-Z]{$1}";
    private static final String DIGITS_AND_A_Z_LETTERS = "[a-zA-Z0-9]{$1}";

    @Autowired
    private CountryConfig countryConfig;

    @Override
    public boolean isValid(IbanDBVO iban) {
        return hasValidCountryCode(iban.getCountryCode()) &&
                hasValidLength(iban) &&
                hasCheckNumberAsDigit(iban) &&
                hasValidBban(iban) &&
                isValid(iban.getCountryCode() + iban.getCheckDigits() + iban.getBban());
    }

    private boolean hasValidBban(IbanDBVO iban) {
        var humanFriendlyBbanPattern = countryConfig.getIbanconfig().get(iban.getCountryCode()).getPattern();
        String regexPattern = createRegex(humanFriendlyBbanPattern);
        var pattern = Pattern.compile(
                regexPattern);
        var matcher = pattern.matcher(iban.getBban());
        return matcher.matches();
    }

    private String createRegex(String humanFriendlyBbanPattern) {
        var regexPattern = humanFriendlyBbanPattern
                .replaceAll("D(\\d\\d)", ONLY_DIGITS)
                .replaceAll("C(\\d\\d)", ONLY_CAPITALS_A_Z)
                .replaceAll("A(\\d\\d)", DIGITS_AND_A_Z_LETTERS)
                .replace("\\{0", "{");
        return regexPattern;
    }

    private int length(IbanDBVO iban) {
        return iban.getCountryCode().length() + iban.getCheckDigits().length() + iban.getBban().length();
    }

    private boolean hasValidCountryCode(String alfa2country) {
        return countryConfig.getIbanconfig().containsKey(alfa2country);
    }

    private boolean hasValidLength(IbanDBVO iban) {
        return countryConfig.getIbanconfig().get(iban.getCountryCode()).getIbanlength() == length(iban);
    }

    private boolean hasCheckNumberAsDigit(IbanDBVO iban) {
        var checkDigits = iban.getCheckDigits();
        return (checkDigits.charAt(0) >= '0' &&
                checkDigits.charAt(0) <= '9' &&
                checkDigits.charAt(1) >= '0' &&
                checkDigits.charAt(1) <= '9');

    }

    private boolean isValid(String ibanAsString) {
        String convertedIban = convertIban(ibanAsString);
        BigInteger iban = new BigInteger(convertedIban);
        var mod97 = BigInteger.valueOf(97);
        return iban.mod(mod97).compareTo(BigInteger.ONE) == 0;
    }

    private String convertIban(String ibanAsString) {
        StringBuilder sb = new StringBuilder(34);
        for (int i = 4; i < ibanAsString.length(); i++) {
            sb.append(replaceLetterWith2Digits(ibanAsString.charAt(i)));
        }
        sb.append(replaceLetterWith2Digits(ibanAsString.charAt(0)))
                .append(replaceLetterWith2Digits(ibanAsString.charAt(1)))
                .append(ibanAsString, 2, 4);
        return sb.toString();
    }

    private String replaceLetterWith2Digits(char letter) {
        if (letter >= '0' && letter <= '9') {
            return String.valueOf(letter);
        } else {
            return String.valueOf(letter - 55);
        }
    }

}
