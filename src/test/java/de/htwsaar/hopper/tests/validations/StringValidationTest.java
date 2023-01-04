package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Utils;
import org.junit.Test;

import static org.junit.Assert.*;


public class StringValidationTest {

    @Test
    public void stringWithSpacesShouldBeTrimmed(){
        String string = "  test  ";
        assertEquals(Utils.validateString(string, "Fehler"), "test");
    }

    @Test
    public void stringWithSeveralSpacesShouldBeTrimmed(){
        String string = "  test   test  ";
        assertEquals(Utils.validateString(string, "Fehler"), "test   test");
    }

    @Test
    public void stringWithSpacesInBetweenShouldRemain(){
        String string = "t es t";
        assertEquals(Utils.validateString(string, "Fehler"), "t es t");
    }

    @Test (expected = IllegalArgumentException.class)
    public void stringOnlyContaingSpacesShouldBeTrimmedAndThrowException(){
        String string = "  ";
        Utils.validateString(string, "Fehler");
    }

    @Test (expected = IllegalArgumentException.class)
    public void emptyStringShouldThrowException(){
        String string = "";
        Utils.validateString(string, "Fehler");
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullStringShouldThrowException(){
        Utils.validateString(null, "Fehler");
    }
}
