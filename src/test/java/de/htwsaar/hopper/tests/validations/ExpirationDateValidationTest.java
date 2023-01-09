package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExpirationDateValidationTest {

    @Test
    public void dateWorking(){
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MONTH, 1);
        Validation.validateExpirationDate(current);
    }
    @Test(expected = IllegalArgumentException.class)
    public void dateBefore() {
        Calendar pastDate = new GregorianCalendar(2014, Calendar.JANUARY, 1);
        Validation.validateExpirationDate(pastDate);
    }
    @Test
    public void dateFuture() {
        Calendar futureDate = new GregorianCalendar(2024, Calendar.JANUARY, 1);
        Validation.validateExpirationDate(futureDate);
    }

    @Test (expected = IllegalArgumentException.class)
    public void dateNull() {
        Validation.validateExpirationDate(null);
    }
}
