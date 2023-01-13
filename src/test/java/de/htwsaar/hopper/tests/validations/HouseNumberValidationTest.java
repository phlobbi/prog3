package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CustomerValidation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Diese Klasse überprüft die HouseNumberValidation.
 */
public class HouseNumberValidationTest {

    @Test
    public void houseNumberWithOneDigitShouldBeTrimmed(){
        String houseNumber = "  1  ";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber, "Fehler"), "1");
    }

    @Test
    public void houseNumberWithOneDigitValid(){
        String houseNumber = "1";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "1");
    }

    @Test
    public void houseNumberWithOneDigitAndLetterValid(){
        String houseNumber = "1a";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "1a");
    }

    @Test
    public void houseNumberWithOneDigitAndLettersShouldBeTrimmed(){
        String houseNumber = "  1a  ";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "1a");
    }

    @Test
    public void houseNumberWithSpacesShouldBeTrimmed(){
        String houseNumber = "  23  ";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "23");
    }

    @Test
    public void houseNumberValid(){
        String houseNumber = "23";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "23");
    }

    @Test
    public void houseNumberValidWithLetter(){
        String houseNumber = "23a";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "23a");
    }

    @Test
    public void houseNumberWithSpacesAndLettersValid(){
        String houseNumber = "  23a  ";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "23a");
    }

    @Test
    public void houseNumberWithThreeDigitsValid(){
        String houseNumber = "123";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "123");
    }

    @Test
    public void houseNumberWithThreeNumbersShouldBeTrimmed(){
        String houseNumber = "  123  ";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "123");
    }

    @Test
    public void houseNumberValidWithThreeDigitsAndLetter(){
        String houseNumber = "123a";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "123a");
    }

    @Test
    public void houseNumberWithThreeDigitsAnsLetterShouldBeTrimmed(){
        String houseNumber = "  123a  ";
        assertEquals(CustomerValidation.validateHouseNumber(houseNumber), "123a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberWithOnlyOneLetter(){
        String houseNumber = "a";
        CustomerValidation.validateHouseNumber(houseNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberNullShouldThrowException(){
        CustomerValidation.validateHouseNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberWithWrongCharactersShouldThrowException(){
        String houseNumber = "23?";
        CustomerValidation.validateHouseNumber(houseNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberEmptyShouldThrowException(){
        String houseNumber = "";
        CustomerValidation.validateHouseNumber(houseNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberWithWrongLengthShouldThrowException(){
        String houseNumber = "23a?";
        CustomerValidation.validateHouseNumber(houseNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberWithTwoLettersShouldThrowException(){
        String houseNumber = "23aa";
        CustomerValidation.validateHouseNumber(houseNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void houseNumberWithFourDigitsShouldThrowException(){
        String houseNumber = "1234a";
        CustomerValidation.validateHouseNumber(houseNumber);
    }

}
