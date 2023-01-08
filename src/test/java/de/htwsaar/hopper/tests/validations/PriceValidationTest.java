package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PriceValidationTest {

    @Test
    public void basePriceNull(){
        int basePrice = 0;
        Validation.validateBasePrice(basePrice);
    }

    @Test
    public void basePricePositive(){
        int basePrice = 1000;
        Validation.validateBasePrice(basePrice);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void basePriceNegative(){
        int basePrice = -1000;
        Validation.validateBasePrice(basePrice);
    }

    @Test
    public void currentPriceNull(){
        int currentPrice = 0;
        Validation.validateBasePrice(currentPrice);
    }

    @Test
    public void currentPricePositive(){
        int currentPrice = 1000;
        Validation.validateBasePrice(currentPrice);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void currentPriceNegative(){
        int currentPrice = -1000;
        Validation.validateBasePrice(currentPrice);
    }

}
