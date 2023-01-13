package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CustomerValidation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Diese Klasse überprüft die DriverLicenseNumberValidation.
 */
public class DriverLicenseNumberValidationTest {

    @Test
    public void driverLicenseWithSpacesShouldBeTrimmed(){
        String driverLicense = "  B072RRE2I55  ";
        assertEquals(CustomerValidation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test
    public void driverLicenseWithLowercaseLettersShouldBeUppercased(){
        String driverLicense = "b072rre2i55";
        assertEquals(CustomerValidation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test
    public void driverLicenseWithLowercaseLettersAndSpacesShouldBeUppercasedAndTrimmed(){
        String driverLicense = "  b072rre2i55  ";
        assertEquals(CustomerValidation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test
    public void driverLicenseValid(){
        String driverLicense = "B072RRE2I55";
        assertEquals(CustomerValidation.validateDriverLicenseNumber(driverLicense), "B072RRE2I55");
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseWithWrongLengthShouldThrowException(){
        String driverLicense = "B072RRE2I5";
        CustomerValidation.validateDriverLicenseNumber(driverLicense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseWithWrongLengthShouldThrowExceptionTwo(){
        String driverLicense = "B072RRE2I555";
        CustomerValidation.validateDriverLicenseNumber(driverLicense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseEmptyShouldThrowException(){
        String driverLicense = "";
        CustomerValidation.validateDriverLicenseNumber(driverLicense);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseNullShouldThrowException(){
        CustomerValidation.validateDriverLicenseNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverLicenseWithWrongCharactersShouldThrowException(){
        String driverLicense = "B072RRE2I5?";
        CustomerValidation.validateDriverLicenseNumber(driverLicense);
    }
}
