package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

public class PriceValidationTest {

    @Test (expected = IllegalArgumentException.class)
    public void basePriceZero(){
        double basePrice = 0.00;
        Validation.validateBasePrice(basePrice);
    }

    @Test
    public void basePricePositive(){
        double basePrice = 1000.00;
        Validation.validateBasePrice(basePrice);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void basePriceNegative(){
        double basePrice = -1000.00;
        Validation.validateBasePrice(basePrice);
    }

    @Test (expected = IllegalArgumentException.class)
    public void currentPriceZero(){
        double currentPrice = 0.00;
        Validation.validateBasePrice(currentPrice);
    }

    @Test
    public void currentPricePositive(){
        double currentPrice = 1000.00;
        Validation.validateBasePrice(currentPrice);
    }

    @Test   (expected = IllegalArgumentException.class)
    public void currentPriceNegative(){
        double currentPrice = -1000.00;
        Validation.validateBasePrice(currentPrice);
    }
}
