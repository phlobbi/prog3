package de.htwsaar.hopper.tests.validations;


import de.htwsaar.hopper.logic.validations.CustomerValidation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class PhoneNumberValidationTest {

    @Test
    public void phoneNumberAccordingToPatternWithSlashShouldBeTrue(){
        String phoneNumber = "0176/12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberAccordingToPatternAndLeadingZeroAndWithoutSlashShouldBeTrue(){
        String phoneNumber = "017612345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberAccordingToPatternAndLeadingPlus49AndWithSlashShouldBeTrue(){
        String phoneNumber = "+49176/12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberAccordingToPatternAndWithPlus49AndWithoutSlashShouldBeTrue(){
        String phoneNumber = "+4917612345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberWithWrongSpecialCharacterShouldThrowException(){
        String phoneNumber = "-017612345678";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberWithoutLeadingZeroShouldThrowException(){
        String phoneNumber = "17612345678";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberContainingLettersShouldThrowException(){
        String phoneNumber = "0176/1234567a";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberContainingSpecialCharactersAtEndShouldThrowException(){
        String phoneNumber = "0176/1234567-";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberContainingSpecialCharactersAtEnd2ShouldThrowException(){
        String phoneNumber = "0176/1234567+";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberOnlyConsistingOfLettersShouldThrowException(){
        String phoneNumber = "geilomatico";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberOnlyConsistingOfSpecialCharactersShouldThrowException(){
        String phoneNumber = "++-";
        CustomerValidation.validatePhoneNumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullPhoneNumberShouldThrowException(){
        CustomerValidation.validatePhoneNumber(null);
    }

    @Test
    public void phoneNumberWithFourDigitRegionShouldBeTrue(){
        String phoneNumber = "01763/12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberWithFourDigitRegionInternationalShouldBeTrue(){
        String phoneNumber = "+491763/12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberWithFourDigitRegionInternationalWithoutSlashShouldBeTrue(){
        String phoneNumber = "+49176312345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberWithFourDigitRegionWithoutSlashShouldBeTrue(){
        String phoneNumber = "0176312345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberWithThreeDigitRegionWhitespaceShouldBeTrue(){
        String phoneNumber = "0173 12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberInternationalWhitespaceShouldBeTrue(){
        String phoneNumber = "+49173 12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberInternationalFourDigitRegionWhitespaceShouldBeTrue(){
        String phoneNumber = "+491734 12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberFourDigitRegionWhitespaceShouldBeTrue(){
        String phoneNumber = "01734 12345678";
        assertEquals(CustomerValidation.validatePhoneNumber(phoneNumber), phoneNumber.trim());
    }
}
