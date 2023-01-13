package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.validations.CarValidation;
import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreatedDateValidationTest {

    @Test
    public void dateWorking(){
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MONTH, -1);
        CarValidation.validateCreatedDate(current);
    }
    @Test
    public void dateBefore() {
        Calendar pastDate = new GregorianCalendar(2014, Calendar.JANUARY, 1);
        CarValidation.validateCreatedDate(pastDate);
    }
    @Test (expected = IllegalArgumentException.class)
    public void dateFuture() {
        Calendar futureDate = new GregorianCalendar(2024, Calendar.JANUARY, 1);
        CarValidation.validateCreatedDate(futureDate);
    }

    @Test (expected = IllegalArgumentException.class)
    public void dateNull() {
        CarValidation.validateCreatedDate(null);
    }
}
