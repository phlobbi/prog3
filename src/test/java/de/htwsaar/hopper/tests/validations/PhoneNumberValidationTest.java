package de.htwsaar.hopper.tests.validations;


import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;
public class PhoneNumberValidationTest {

    @Test
    public void phoneNumberAccordingToPatternWithBackslashShouldBeTrue(){
        String phoneNumber = "0176/12345678";
        assertEquals(Validation.validatePhonenumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberAccordingToPatternAndLeadingZeroAndWithoutBackslashShouldBeTrue(){
        String phoneNumber = "017612345678";
        assertEquals(Validation.validatePhonenumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberAccordingToPatternAndLeadingPlus49AndWithBackslashShouldBeTrue(){
        String phoneNumber = "+49176/12345678";
        assertEquals(Validation.validatePhonenumber(phoneNumber), phoneNumber.trim());
    }

    @Test
    public void phoneNumberAccordingToPatternAndWithPlus49AndWithoutBackslashShouldBeTrue(){
        String phoneNumber = "+4917612345678";
        assertEquals(Validation.validatePhonenumber(phoneNumber), phoneNumber.trim());
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberWithWhiteSpaceInBetweenShouldThrowException(){
        String phoneNumber = "0176 12345678";
        Validation.validatePhonenumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberWithWrongSpecialCharacterShouldThrowException(){
        String phoneNumber = "-017612345678";
        Validation.validatePhonenumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberWithoutLeadingZeroShouldThrowException(){
        String phoneNumber = "17612345678";
        Validation.validatePhonenumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberContainingLettersShouldThrowException(){
        String phoneNumber = "0176/1234567a";
        Validation.validatePhonenumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberContainingSpecialCharactersAtEndShouldThrowException(){
        String phoneNumber = "0176/1234567-";
        Validation.validatePhonenumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberContainingSpecialCharactersAtEnd2ShouldThrowException(){
        String phoneNumber = "0176/1234567+";
        Validation.validatePhonenumber(phoneNumber);
    }

    @Test (expected = IllegalArgumentException.class)
    public void phoneNumberOnlyConsistingOfLettersShouldThrowException(){
        String phoneNumber = "geilomatico";
        Validation.validatePhonenumber(phoneNumber);
    }
}
