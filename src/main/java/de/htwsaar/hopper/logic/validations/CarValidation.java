package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;

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




    public static String validateLicensePlate(String licensePlate) {
        licensePlate = validateString(licensePlate, "Die Kennzeichen dürfen nicht leer sein!");
        licensePlate = licensePlate.toUpperCase();
        Utils.check(licensePlate.matches(ValidationRegexEnum.LICENSE_PLATE.getRegex()), "Kennezeichen ist nicht valide!");
        return licensePlate;
    }

}
