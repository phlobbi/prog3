package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverLicenseNumberValidationTest {

    @Test
    public void driverLicenseWithSpacesShouldBeTrimmed(){
        String driverLicense = "  B072RRE2I55  ";
        assertEquals(Validation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test
    public void driverLicenseWithLowercaseLettersShouldBeUppercased(){
        String driverLicense = "b072rre2i55";
        assertEquals(Validation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test
    public void driverLicenseWithLowercaseLettersAndSpacesShouldBeUppercasedAndTrimmed(){
        String driverLicense = "  b072rre2i55  ";
        assertEquals(Validation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test
    public void driverLicenseValid(){
        String driverLicense = "B072RRE2I55";
        assertEquals(Validation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseWithWrongLengthShouldThrowException(){
        String driverLicense = "B072RRE2I5";
        Validation.validateDriverLicenseNumber(driverLicense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseWithWrongLengthShouldThrowExceptionTwo(){
        String driverLicense = "B072RRE2I555";
        Validation.validateDriverLicenseNumber(driverLicense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseEmptyShouldThrowException(){
        String driverLicense = "";
        Validation.validateDriverLicenseNumber(driverLicense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseNullShouldThrowException(){
        Validation.validateDriverLicenseNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseWithWrongCharactersShouldThrowException(){
        String driverLicense = "B072RRE2I5?";
        Validation.validateDriverLicenseNumber(driverLicense);
    }
}
