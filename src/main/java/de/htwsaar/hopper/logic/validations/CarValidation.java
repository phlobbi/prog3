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


    /**
     * Überprüft das Nummernschild auf Gültigkeit
     *
     * @param licensePlate Nummernschild
     * @return getrimmtes Nummernschild
     * @throws IllegalArgumentException wenn Nummernschild nicht passt
     */
    public static String validateLicensePlate(String licensePlate) {
        licensePlate = validateString(licensePlate, "Die Kennzeichen dürfen nicht leer sein!");
        licensePlate = licensePlate.toUpperCase();
        Utils.check(licensePlate.matches(ValidationRegexEnum.LICENSE_PLATE.getRegex()), "Kennezeichen ist nicht valide!");
        return licensePlate;
    }


    /**
     * Prüft ob die Anzahl Sitze stimmen kann
     *
     * @param seats Anzahl Sitze
     * @return Sitze wenn gut
     * @throws IllegalArgumentException wenn die Anzahl Sitze nicht stimmt
     */
    public static int validateSeats(int seats) {
        Utils.check(seats > 0, "Die Anzahl der Sitze muss größer als 0 sein!");
        Utils.check(seats < 302, "Die Anzahl der Sitze darf nicht größer als 301 sein!\n" +
                "Der Volvo Gran Artic 300 mit 301 Sitzen ist der größte Bus der Welt!"); //Schleichwerbung? rausnehmen? :D
        return seats;
    }

}
