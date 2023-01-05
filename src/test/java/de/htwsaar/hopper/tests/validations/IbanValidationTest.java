package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

public class IbanValidationTest {
    private final static String EXPECTED_IBAN = "DE89 3704 0044 0532 0130 00";

    @Test
    public void ibanValid() {
        String iban = "DE89370400440532013000";
        assertEquals(Validation.validateIBAN(iban), EXPECTED_IBAN);
    }

    @Test
    public void ibanValidWithSpaces() {
        String iban = "DE89 3704 0044 0532 0130 00";
        assertEquals(Validation.validateIBAN(iban), EXPECTED_IBAN);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanValidWithSpacesAndLowercaseShouldThrowException() {
        String iban = "de89 3704 0044 0532 0130 00";
        Validation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanValidWithLowercaseCountrycodeShouldThrowException() {
        String iban = "de89370400440532013000";
        Validation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanWithTooFewLettersShouldThrowException() {
        String iban = "DE8937040044053201300";
        Validation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanWithTooManyLettersShouldThrowException() {
        String iban = "DE893704004405320130000";
        Validation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanWithoutCountryCodeShouldThrowException() {
        String iban = "370400440532013000";
        Validation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanEmptyStringShouldThrowException() {
        Validation.validateIBAN("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void IbanNullStringShouldThrowException() {
        Validation.validateIBAN(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void IbanWithInvalidChecksumShouldThrowException() {
        String iban = "DE89370400440532013001";
        Validation.validateIBAN(iban);
    }
}
