package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

public class SeatsValidationTest {

    @Test
    public void seatsNull(){
        int seats = 0;
        Validation.validateSeats(seats);
    }

    @Test
    public void seatsHundred(){
        int seats = 100;
        Validation.validateSeats(seats);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void seatsTooMany(){
        int seats = 302;
        Validation.validateBasePrice(seats);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void seatsNegative(){
        int seats = -302;
        Validation.validateBasePrice(seats);
    }
}
