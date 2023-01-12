package de.htwsaar.hopper.logic.validations;

import java.util.Calendar;

public class CarValidation extends Validation{

    public static double validateBasePrice(double basePrice) {
        Utils.check(basePrice > 0.00, "Der Basispreis muss größer als 0 sein!");
        return basePrice;
    }

    public static double validateCurrentPrice(double currentPrice) {
        Utils.check(currentPrice > 0.00, "Der aktuelle Preis muss größer als 0 sein!");
        return currentPrice;
    }


    /**
     * Prüft ob Erstellungsdatum nicht in der Zukunft liegt
     *
     * @param date
     * @return
     */
    public static Calendar validateCreatedDate(Calendar date) {
        return validateDateFutureForbidden(date);
    }

}
