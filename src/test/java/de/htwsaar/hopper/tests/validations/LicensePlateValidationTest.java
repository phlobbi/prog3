package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LicensePlateValidationTest {

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateCompletelyEmpty(){
        String license = "";
        Validation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateOnlyNumbers(){
        String license = "69-96-69";
        Validation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateOnlyLetters(){
        String license = "XD-ke-kW";
        Validation.validateLicensePlate(license);
    }

    @Test (expected = IllegalArgumentException.class)
    public void licensePlateLetterAndNumberAtBeginning(){
        String license = "X1-23-45";
        Validation.validateLicensePlate(license);
    }

    @Test
    public void licensePlateWorking(){
        String license = "SB-42-69";
        Validation.validateLicensePlate(license);
    }

}
