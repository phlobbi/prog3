package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CustomerValidation;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Diese Klasse überprüft die ExpirationDateValidation.
 */
public class ExpirationDateValidationTest {

    @Test
    public void dateWorking(){
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MONTH, 1);
        CustomerValidation.validateExpirationDate(current);
    }
    @Test(expected = IllegalArgumentException.class)
    public void dateBefore() {
        Calendar pastDate = new GregorianCalendar(2014, Calendar.JANUARY, 1);
        CustomerValidation.validateExpirationDate(pastDate);
    }
    @Test
    public void dateFuture() {
        Calendar futureDate = new GregorianCalendar(2024, Calendar.JANUARY, 1);
        CustomerValidation.validateExpirationDate(futureDate);
    }

    @Test (expected = IllegalArgumentException.class)
    public void dateNull() {
        CustomerValidation.validateExpirationDate(null);
    }
}
