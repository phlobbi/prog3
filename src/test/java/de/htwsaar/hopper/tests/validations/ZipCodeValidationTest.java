package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZipCodeValidationTest {

    @Test
    public void zipCodeWith5DigitsValidMinValueShouldBeValid(){
        String zipCode = "01000";
        assertEquals(Validation.validateZipCode(zipCode), zipCode);
    }

    @Test
    public void zipCodeWith5DigitsValidMaxValueShouldBeValid(){
        String zipCode = "99999";
        assertEquals(Validation.validateZipCode(zipCode), zipCode);
    }

    @Test
    public void zipCodeWithWhiteSpaceAtEndShouldBeValid() {
        String zipCode = "01000 ";
        Validation.validateZipCode(zipCode);
    }

    @Test
    public void zipCodeWithWhiteSpaceAtStartShouldBeValid() {
        String zipCode = " 01000";
        Validation.validateZipCode(zipCode);
    }

    @Test
    public void zipCodeWithWhiteSpaceAtStartAndEndShouldBeValid() {
        String zipCode = " 01000 ";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithWhiteSpaceInBetweedShouldThrowException(){
        String zipCode = "999 99";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeUnderMinValueShouldThrowException(){
        String zipCode = "00999";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWith0DigitsShouldThrowException(){
        String zipCode = "";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWith4DigitsShouldThrowException(){
        String zipCode = "9999";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWith6DigitsShouldThrowException(){
        String zipCode = "999999";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeNullShouldThrowException(){
        Validation.validateZipCode(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeNegativeShouldThrowException(){
        String negativeZipCode = "-10000";
        Validation.validateZipCode(negativeZipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithForbiddenCharacterShouldThrowException() {
        String zipCode = "01_000";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithForbiddenCharacter2ShouldThrowException() {
        String zipCode = "01-000";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithForbiddenCharacter3ShouldThrowException() {
        String zipCode = "01.000";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithForbiddenCharacter4ShouldThrowException() {
        String zipCode = "01/000";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithForbiddenCharacter5ShouldThrowException() {
        String zipCode = "/b01000";
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithForbiddenCharacter6ShouldThrowException() {
        String zipCode = "01000a";
        Validation.validateZipCode(zipCode);
    }

}
