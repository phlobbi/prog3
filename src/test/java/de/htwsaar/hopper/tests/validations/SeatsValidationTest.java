package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CarValidation;
import org.junit.Test;

public class SeatsValidationTest {

    @Test (expected = IllegalArgumentException.class)
    public void seatsZero(){
        int seats = 0;
        CarValidation.validateSeats(seats);
    }

    @Test
    public void seatsHundred(){
        int seats = 100;
        CarValidation.validateSeats(seats);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void seatsTooMany(){
        int seats = 302;
       CarValidation.validateSeats(seats);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void seatsNegative(){
        int seats = -302;
        CarValidation.validateSeats(seats);
    }

}
