package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;
import de.htwsaar.hopper.logic.validations.CustomerValidation;
import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Diese Klasse überprüft die StringValidation.
 */
public class StringValidationTest {

    @Test
    public void stringWithSpacesShouldBeTrimmed(){
        String string = "  test  ";
        assertEquals(Validation.validateString(string, "Fehler"), "test");
    }

    @Test
    public void stringWithSeveralSpacesShouldBeTrimmed(){
        String string = "  test   test  ";
        assertEquals(Validation.validateString(string, "Fehler"), "test   test");
    }

    @Test
    public void stringWithSpacesInBetweenShouldRemain(){
        String string = "t es t";
        assertEquals(Validation.validateString(string, "Fehler"), "t es t");
    }

    @Test (expected = IllegalArgumentException.class)
    public void stringOnlyContaingSpacesShouldBeTrimmedAndThrowException(){
        String string = "  ";
        Validation.validateString(string, "Fehler");
    }

    @Test (expected = IllegalArgumentException.class)
    public void emptyStringShouldThrowException(){
        String string = "";
        Validation.validateString(string, "Fehler");
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullStringShouldThrowException(){
        Validation.validateString(null, "Fehler");
    }

    @Test
    public void stringRegexValidation1True(){
        String string = "12a";
        CustomerValidation.validateStringViaRegex(string, ValidationRegexEnum.HOUSE_NUMBER.getRegex(),"Fehler");
    }
}
