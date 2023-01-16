package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;

import java.util.Calendar;

/**
 * Validierungsklasse fuer Buchungen.
 * @author Bennet
 */
public class CarValidation extends Validation {

    /**
     * Prüft, ob der übergebene Grundpreis gültig ist.
     * @param basePrice Zu prüfender Grundpreis
     * @return Grundpreis, falls gültig
     * @throws IllegalArgumentException Falls der Grundpreis kleiner als 0 ist
     */
    public static double validateBasePrice(double basePrice) {
        Utils.check(basePrice > 0.00, "Der Basispreis muss größer als 0 sein!");
        return basePrice;
    }

    /**
     * Prüft, ob der übergebene Tagespreis gültig ist.
     * @param currentPrice Zu prüfender Tagespreis
     * @return Tagespreis, falls gültig
     * @throws IllegalArgumentException Falls der Tagespreis kleiner als 0 ist
     */
    public static double validateCurrentPrice(double currentPrice) {
        Utils.check(currentPrice > 0.00, "Der aktuelle Preis muss größer als 0 sein!");
        return currentPrice;
    }

    /**
     * Prüft, ob das Erstellungsdatum gültig ist.
     * Es wäre zum Beispiel ungültig, wenn es in der Zukunft liegt.
     * @param date Zu prüfendes Erstellungsdatum
     * @return Erstellungsdatum, falls gültig
     * @throws IllegalArgumentException Falls das Erstellungsdatum ungültig ist
     */
    public static Calendar validateCreatedDate(Calendar date) {
        return validateDateFutureForbidden(date);
    }


    /**
     * Überprüft, ob das Nummernschild gültig ist.
     * Es muss hierbei sowohl vom Format stimmen als auch die richtigen Zeichen enthalten.
     * Die Methode trimmt den übergebenen String außerdem von jeglichen Leerzeichen.
     * @param licensePlate Zu prüfendes Nummernschild
     * @return getrimmtes Nummernschild
     * @throws IllegalArgumentException Falls ein Nummernschild nicht gültig ist
     */
    public static String validateLicensePlate(String licensePlate) {
        licensePlate = validateString(licensePlate, "Die Kennzeichen dürfen nicht leer sein!");
        licensePlate = licensePlate.toUpperCase();
        Utils.check(licensePlate.matches(ValidationRegexEnum.LICENSE_PLATE.getRegex()), "Kennezeichen ist nicht valide!");
        return licensePlate;
    }


    /**
     * Prüft, ob die Anzahl Sitze stimmen kann
     * @param seats Anzahl der Sitze
     * @return Anzahl der Sitze, falls gültig
     * @throws IllegalArgumentException Falls die Anzahl der Sitze nicht stimmen kann
     */
    public static int validateSeats(int seats) {
        Utils.check(seats > 0, "Die Anzahl der Sitze muss größer als 0 sein!");
        Utils.check(seats < 302, "Die Anzahl der Sitze darf nicht größer als 301 sein!\n" +
                "Der Volvo Gran Artic 300 mit 301 Sitzen ist der größte Bus der Welt!"); //Schleichwerbung? rausnehmen? :D
        return seats;
    }

    /**
     * Prüft, ob der übergebene Typ gültig ist.
     * Da es um eine Enum geht, wird hier nur geprüft, ob der übergebene Typ null ist.
     * @param type Zu prüfender Typ
     * @return Typ, falls gültig
     * @throws IllegalArgumentException Falls der Typ null ist
     */
    public static CarTypeEnum validateCarType(CarTypeEnum type){
        Utils.check(type !=null, "Der Auto-Typ darf nicht null sein.");
        return type;
    }
}
