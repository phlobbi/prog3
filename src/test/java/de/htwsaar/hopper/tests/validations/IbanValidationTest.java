package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CustomerValidation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Diese Klasse überprüft die IbanValidation.
 */
public class IbanValidationTest {
    private final static String EXPECTED_IBAN = "DE89370400440532013000";

    @Test
    public void ibanValid() {
        String iban = "DE89370400440532013000";
        assertEquals(CustomerValidation.validateIBAN(iban), EXPECTED_IBAN);
    }

    @Test
    public void ibanValidWithSpaces() {
        String iban = "DE89 3704 0044 0532 0130 00";
        assertEquals(CustomerValidation.validateIBAN(iban), EXPECTED_IBAN);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanValidWithSpacesAndLowercaseShouldThrowException() {
        String iban = "de89 3704 0044 0532 0130 00";
        CustomerValidation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanValidWithLowercaseCountrycodeShouldThrowException() {
        String iban = "de89370400440532013000";
        CustomerValidation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanWithTooFewLettersShouldThrowException() {
        String iban = "DE8937040044053201300";
        CustomerValidation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanWithTooManyLettersShouldThrowException() {
        String iban = "DE893704004405320130000";
        CustomerValidation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanWithoutCountryCodeShouldThrowException() {
        String iban = "370400440532013000";
        CustomerValidation.validateIBAN(iban);
    }

    @Test (expected = IllegalArgumentException.class)
    public void ibanEmptyStringShouldThrowException() {
        CustomerValidation.validateIBAN("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void IbanNullStringShouldThrowException() {
        CustomerValidation.validateIBAN(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void IbanWithInvalidChecksumShouldThrowException() {
        String iban = "DE89370400440532013001";
        CustomerValidation.validateIBAN(iban);
    }

    @Test
    public void IbanWithAdditionalWhitespaceShouldProcess() {
        String iban = "  DE89 3704 0044 0532 0130 00  ";
        assertEquals(CustomerValidation.validateIBAN(iban), EXPECTED_IBAN);
    }
}
