package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZipCodeValidationTest {

    @Test
    public void zipCodeWith5DigitsValidMinValue(){
        int zipCode = 10000;
        assertEquals(Validation.validateZipCode(zipCode), zipCode);
    }

    @Test
    public void zipCodeWith5DigitsValidMaxValue(){
        int zipCode = 99999;
        assertEquals(Validation.validateZipCode(zipCode), zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWith4DigitsShouldThrowException(){
        int zipCode = 1000;
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWith6DigitsShouldThrowException(){
        int zipCode = 100000;
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeZeroShouldThrowException(){
        int zero = 0;
        Validation.validateZipCode(zero);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeNegativeShouldThrowException(){
        int negativeZipCode = -10000;
        Validation.validateZipCode(negativeZipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithOctalValueShouldThrowException(){
        int zipCode = 01_000;
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithHexValueShouldThrowException(){
        int zipCode = 0x1000;
        Validation.validateZipCode(zipCode);
    }

    @Test (expected = IllegalArgumentException.class)
    public void zipCodeWithBinaryValueShouldThrowException(){
        int zipCode = 0b1000;
        Validation.validateZipCode(zipCode);
    }

}
