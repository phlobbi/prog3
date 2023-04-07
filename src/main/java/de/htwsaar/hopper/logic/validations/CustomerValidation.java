package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;
import nl.garvelink.iban.IBAN;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Calendar;

/**
 * Stellt statische Methoden zur Verfügung, um die Felder für die Klasse Customer zu validieren.
 */
public final class CustomerValidation extends Validation {

    /**
     * Privater Konstruktor, um eine Instanziierung zu verhindern.
     */
    private CustomerValidation() {
    }

    /**
     * Prüft, ob eine IBAN gültig ist.
     * Die Methode entfernt außerdem sämtliche Leerzeichen um die IBAN.
     *
     * @param iban Zu prüfende IBAN
     * @return IBAN als String, ohne Separierung
     * @throws IllegalArgumentException Falls die IBAN ungültig ist
     * @throws IllegalArgumentException Falls die IBAN leer ist
     * @throws IllegalArgumentException Falls die IBAN null ist
     */
    public static String validateIBAN(String iban) {
        IBAN ibanObject = IBAN.valueOf(validateString(iban, "Die IBAN darf nicht leer sein."));
        return ibanObject.toPlainString();
    }

    /**
     * Prüft, ob eine E-Mail gültig ist.
     * Die Methode entfernt außerdem sämtliche Leerzeichen um die E-Mail.
     *
     * @param email E-Mail, die überprüft werden soll
     * @return Getrimmte E-Mail, falls gültig
     * @throws IllegalArgumentException Falls die E-Mail ungültig ist
     * @throws IllegalArgumentException Falls die E-Mail leer ist
     * @throws IllegalArgumentException Falls die E-Mail null ist
     */
    public static String validateEmail(String email) {
        //DomainValidator für den EmailValidator
        DomainValidator domainValidator = DomainValidator.getInstance(false);

        //Angaben ob Local und TLD erlaubt sind
        EmailValidator emailValidator = new EmailValidator(false, false, domainValidator);

        //trimmen
        email = validateString(email, "Die E-Mail darf nicht leer sein.");

        //prüfen ob Mail gültig ist
        if (emailValidator.isValid(email)) {
            return email;
        } else {
            throw new IllegalArgumentException("Die E-Mail ist ungültig!");
        }
    }

    /**
     * Prüft, ob eine Telefonnummer gültig ist.
     * Die Methode beschränkt sich ausschließlich auf deutsche Telefonnummern.
     * Sie trimmt außerdem die Telefonnummer.
     * Zusätzlich kann hier noch eine Fehlermeldung mitgegeben werden, falls die Telefonnummer ungültig sein sollte.
     *
     * @param phoneNumber  Zu prüfende Telefonnummer
     * @param errorMessage Fehlermeldung, falls die Telefonnummer ungültig ist
     * @return getrimmte Telefonnummer
     * @throws IllegalArgumentException Falls die Telefonnummer ungültig ist
     * @throws IllegalArgumentException Falls die Telefonnummer leer ist
     * @throws IllegalArgumentException Falls die Telefonnummer null ist
     */
    public static String validatePhoneNumber(String phoneNumber, String errorMessage) {
        return validateStringViaRegex(phoneNumber, ValidationRegexEnum.PHONE_NUMBER.getRegex(), errorMessage);
    }

    /**
     * Prüft, ob eine Telefonnummer gültig ist.
     * Die Methode beschränkt sich ausschließlich auf deutsche Telefonnummern.
     * Sie trimmt außerdem die Telefonnummer.
     *
     * @param phoneNumber Zu prüfende Telefonnummer
     * @return getrimmte Telefonnummer
     * @throws IllegalArgumentException Falls die Telefonnummer ungültig ist
     * @throws IllegalArgumentException Falls die Telefonnummer leer ist
     * @throws IllegalArgumentException Falls die Telefonnummer null ist
     */
    public static String validatePhoneNumber(String phoneNumber) {
        return validateStringViaRegex(phoneNumber, ValidationRegexEnum.PHONE_NUMBER.getRegex(), "Die Telefonnummer darf nicht leer sein.");
    }


    /**
     * Prüft, ob eine Führerscheinnummer gültig ist.
     * Es sind nur deutsche Führerscheinnummer erlaubt.
     * Die Methode trimmt außerdem die Führerscheinnummer.
     *
     * @param driverLicenseNumber Führerscheinnummer, die überprüft werden soll
     * @return Geprüfte Führerscheinnummer
     * @throws IllegalArgumentException Falls die Führerscheinnummer ungültig ist
     * @throws IllegalArgumentException Falls die Führerscheinnummer leer ist
     * @throws IllegalArgumentException Falls die Führerscheinnummer null ist
     */
    public static String validateDriverLicenseNumber(String driverLicenseNumber) {
        //  Führerscheinnummer überprüfen
        driverLicenseNumber = validateString(driverLicenseNumber, "Die Führerscheinnummer darf nicht leer sein!");
        Utils.check(driverLicenseNumber.matches(ValidationRegexEnum.DRIVER_LICENSE_NUMBER.getRegex()),
                "Die Führerscheinnummer ist ungültig!");

        //  String nur in Großbuchstaben darstellen
        driverLicenseNumber = driverLicenseNumber.toUpperCase();

        //  Buchstaben in Zahlen umwandeln und mit 9 addieren
        char[] charArray = driverLicenseNumber.toCharArray();
        int[] intArray = new int[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                intArray[i] = 9 + ((char) (charArray[i] - 'A' + 1));
            } else {
                intArray[i] = Integer.parseInt(String.valueOf(charArray[i]));
            }
        }

        //  Prüfziffer ausrechnen
        int checkNumber = 0;
        int j = 9;
        for (int i = 0; i < intArray.length - 2; i++) {
            checkNumber += j * intArray[i];
            j--;
        }
        checkNumber = checkNumber % 11;

        // Prüfziffer prüfen
        Utils.check(checkNumber == intArray[9], "Prüfziffer stimmt nicht überein");

        return driverLicenseNumber;
    }

    /**
     * Prüft das Ablaufdatum eines Führerscheins auf Gültigkeit.
     * Es wäre zum Beispiel ungültig, wenn das Ablaufdatum in der Vergangenheit liegt.
     *
     * @param date Führerscheinablaufdatum
     * @return Datum, falls gültig
     * @throws IllegalArgumentException Falls das Datum ungültig ist
     * @throws IllegalArgumentException Falls das Datum null ist
     */
    public static Calendar validateExpirationDate(Calendar date) {
        return validateDatePastForbidden(date);
    }

    /**
     * Prüft, ob eine Hausnummer gültig ist.
     * Dabei wären zum Beispiel 1 oder 1a erlaubt.
     * Die Hausnummer wird ebenfalls getrimmt.
     * Zusätzlich kann hier noch eine Fehlermeldung mitgegeben werden, falls die Hausnummer ungültig sein sollte.
     *
     * @param houseNumber  Zu prüfende Hausnummer
     * @param errorMessage Fehlermeldung, falls die Hausnummer ungültig ist
     * @return getrimmte Hausnummer
     * @throws IllegalArgumentException Falls die Hausnummer ungültig ist
     * @throws IllegalArgumentException Falls die Hausnummer leer ist
     * @throws IllegalArgumentException Falls die Hausnummer null ist
     */
    public static String validateHouseNumber(String houseNumber, String errorMessage) {
        return validateStringViaRegex(houseNumber, ValidationRegexEnum.HOUSE_NUMBER.getRegex(), errorMessage);
    }

    /**
     * Prüft, ob eine Hausnummer gültig ist.
     * Dabei wären zum Beispiel 1 oder 1a erlaubt.
     * Die Hausnummer wird ebenfalls getrimmt.
     *
     * @param houseNumber Zu prüfende Hausnummer
     * @return getrimmte Hausnummer
     * @throws IllegalArgumentException Falls die Hausnummer ungültig ist
     * @throws IllegalArgumentException Falls die Hausnummer leer ist
     * @throws IllegalArgumentException Falls die Hausnummer null ist
     */
    public static String validateHouseNumber(String houseNumber) {
        return validateStringViaRegex(houseNumber, ValidationRegexEnum.HOUSE_NUMBER.getRegex(), "Die Hausnummer ist ungültig");
    }

    /**
     * Prüft, ob eine Postleitzahl gültig ist.
     * Es sind nur deutsche Postleitzahlen erlaubt.
     * Die Postleitzahl wird ebenfalls getrimmt.
     * Zusätzlich kann hier noch eine Fehlermeldung mitgegeben werden, falls die Postleitzahl ungültig sein sollte.
     *
     * @param zipCode      PLZ, die überprüft werden soll
     * @param errorMessage Fehlermeldung, falls die PLZ ungültig ist
     * @return getrimmte PLZ
     * @throws IllegalArgumentException Falls die PLZ ungültig ist
     * @throws IllegalArgumentException Falls die PLZ leer ist
     * @throws IllegalArgumentException Falls die PLZ null ist
     */
    public static String validateZipCode(String zipCode, String errorMessage) {
        return validateStringViaRegex(zipCode, ValidationRegexEnum.GERMAN_ZIP_CODE.getRegex(), errorMessage);
    }

    /**
     * Prüft, ob eine Postleitzahl gültig ist.
     * Es sind nur deutsche Postleitzahlen erlaubt.
     * Die Postleitzahl wird ebenfalls getrimmt.
     *
     * @param zipCode PLZ, die überprüft werden soll
     * @return getrimmte PLZ
     * @throws IllegalArgumentException Falls die PLZ ungültig ist
     * @throws IllegalArgumentException Falls die PLZ leer ist
     * @throws IllegalArgumentException Falls die PLZ null ist
     */
    public static String validateZipCode(String zipCode) {
        return validateStringViaRegex(zipCode, ValidationRegexEnum.GERMAN_ZIP_CODE.getRegex(), "Die Postleitzahl ist ungültig");
    }

}
