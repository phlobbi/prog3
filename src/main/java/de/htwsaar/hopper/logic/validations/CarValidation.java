package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;

import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Validierungen für Buchungen.
 *
 * @author Bennet
 */
public final class CarValidation extends Validation {

    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");

    /**
     * Privater Konstruktor, um Instanziierung zu verhindern.
     */
    private CarValidation() {
    }

    /**
     * Prüft, ob der übergebene Grundpreis gültig ist.
     *
     * @param basePrice Zu prüfender Grundpreis
     * @return Grundpreis, falls gültig
     * @throws IllegalArgumentException Falls der Grundpreis kleiner als 0 ist
     */
    public static double validateBasePrice(double basePrice) {
        Utils.check(basePrice > 0.00, bundle.getString("BASE_PRICE_GREATER_THAN_ZERO"));
        return basePrice;
    }

    /**
     * Prüft, ob der übergebene Tagespreis gültig ist.
     *
     * @param currentPrice Zu prüfender Tagespreis
     * @return Tagespreis, falls gültig
     * @throws IllegalArgumentException Falls der Tagespreis kleiner als 0 ist
     */
    public static double validateCurrentPrice(double currentPrice) {
        Utils.check(currentPrice > 0.00, bundle.getString("CURRENT_PRICE_GREATER_THAN_ZERO"));
        return currentPrice;
    }

    /**
     * Prüft, ob das Erstellungsdatum gültig ist.
     * Es wäre zum Beispiel ungültig, wenn es in der Zukunft liegt.
     *
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
     *
     * @param licensePlate Zu prüfendes Nummernschild
     * @return getrimmtes Nummernschild
     * @throws IllegalArgumentException Falls ein Nummernschild nicht gültig ist
     */
    public static String validateLicensePlate(String licensePlate) {
        licensePlate = validateString(licensePlate, bundle.getString("LICENSE_PLATE_EMPTY"));
        licensePlate = licensePlate.toUpperCase();
        Utils.check(licensePlate.matches(ValidationRegexEnum.LICENSE_PLATE.getRegex()), bundle.getString("LICENSE_PLATE_NOT_VALID"));
        return licensePlate;
    }


    /**
     * Prüft, ob die Anzahl Sitze stimmen kann
     *
     * @param seats Anzahl der Sitze
     * @return Anzahl der Sitze, falls gültig
     * @throws IllegalArgumentException Falls die Anzahl der Sitze nicht stimmen kann
     */
    public static int validateSeats(int seats) {
        Utils.check(seats > 0, bundle.getString("SEATS_GREATER_THAN_ZERO"));
        Utils.check(seats < 302, bundle.getString("SEATS_LESS_THAN_301"));
        return seats;
    }

    /**
     * Prüft, ob der übergebene Typ gültig ist.
     * Da es um eine Enum geht, wird hier nur geprüft, ob der übergebene Typ null ist.
     *
     * @param type Zu prüfender Typ
     * @return Typ, falls gültig
     * @throws IllegalArgumentException Falls der Typ null ist
     */
    public static CarTypeEnum validateCarType(CarTypeEnum type) {
        Utils.check(type != null, bundle.getString("CAR_TYPE_EMPTY"));
        return type;
    }

    /**
     * Prüft, ob die angegebene Pferdestärke eines Autos größer als null ist
     *
     * @param horsepower Pferdestärke
     * @return horsepower, falls gültig
     * @throws IllegalArgumentException Falls die Pferdestärke nicht stimmt
     */
    public static int validateHorsepower(int horsepower) {
        Utils.check(horsepower > 0, bundle.getString("HORSEPOWER_GREATER_THAN_ZERO"));
        return horsepower;
    }

    /**
     * Prüft, ob der übergebene Typ gültig ist.
     * Da es um eine Enum geht, wird hier nur geprüft, ob der übergebene Typ null ist.
     *
     * @param type Zu prüfender Typ
     * @return Typ, falls gültig
     * @throws IllegalArgumentException Falls der Typ null ist
     */
    public static TransmissionTypeEnum validateTransmissionType(TransmissionTypeEnum type) {
        Utils.check(type != null, bundle.getString("TRANSMISSION_TYPE_EMPTY"));
        return type;
    }

    /**
     * Prüft, ob der übergebene Typ gültig ist.
     * Da es um eine Enum geht, wird hier nur geprüft, ob der übergebene Typ null ist.
     *
     * @param type Zu prüfender Typ
     * @return Typ, falls gültig
     * @throws IllegalArgumentException Falls der Typ null ist
     */
    public static FuelTypeEnum validateFuelType(FuelTypeEnum type) {
        Utils.check(type != null, bundle.getString("FUEL_TYPE_EMPTY"));
        return type;
    }

    /**
     * Prüft, ob der angegebene Kilometerstand eines Autos größer gleich als null ist
     *
     * @param mileage, der Kilometerstand eines Autos
     * @return mileage, falls gültig
     * @throws IllegalArgumentException Falls der Kilometerstand nicht stimmt
     */
    public static int validateMileage(int mileage) {
        Utils.check(mileage >= 0, bundle.getString("MILEAGE_GREATER_THAN_ZERO"));
        return mileage;
    }
}
