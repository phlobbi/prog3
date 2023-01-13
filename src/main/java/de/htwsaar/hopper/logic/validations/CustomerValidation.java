package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;
import nl.garvelink.iban.IBAN;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Calendar;

public class CustomerValidation extends Validation{

    /**
     * Prüft, ob eine IBAN gültig ist.
     *
     * @param iban Zu prüfende IBAN
     * @return IBAN als String mit Separierung nach 4 Zeichen
     * @throws IllegalArgumentException Wenn die IBAN ungültig ist
     */
    public static String validateIBAN(String iban) {
        IBAN ibanObject = IBAN.valueOf(validateString(iban, "Die IBAN darf nicht leer sein."));
        return ibanObject.toPlainString();
    }


    /**
     * Prüft, ob eine E-Mail gültig ist.
     *
     * @param email E-Mail, die überprüft werden soll
     * @return Getrimmte E-Mail, falls gültig
     * @throws IllegalArgumentException Wenn die E-Mail ungültig ist
     */
    public static String validateEmail(String email) {
        //disallow Localhost mails
        boolean allowLocal = false;

        /*
        AllowTld hab ich aus Testcases geschlossen, dass es false sein muss
        Hab nämlich bei mailWithoutTldNotWorking() gesehen, dass es mit true für ne @hotmail
        einfach durchgewunken wird, daher müsste false gehen, falls euch noch Testcases einfallen,
        könnt ihr die gerne hinzufügen!
         */
        boolean allowTld = false;

        //DomainValidator für den EmailValidator
        DomainValidator domainValidator = DomainValidator.getInstance(allowLocal);

        //Angaben ob Local und TLD erlaubt sind
        EmailValidator emailValidator = new EmailValidator(allowLocal, allowTld, domainValidator);

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
     * Telefonnummer prüfen mit Fehlermeldung mitgeben
     *
     * @param phoneNumber Telefonnummer
     * @param errorMessage Fehlermeldung
     * @return getrimmte Telefonnummer
     * @throws IllegalArgumentException wenn Telefonnummer falsch
     */
    public static String validatePhoneNumber(String phoneNumber, String errorMessage) {
        return validateStringViaRegex(phoneNumber, ValidationRegexEnum.PHONE_NUMBER.getRegex(), errorMessage);
    }

    /**
     * Telefonnummer prüfen
     *
     * @param phoneNumber Telefonnummer
     * @return getrimmte Telefonnummer
     * @throws IllegalArgumentException wenn Telefonnummer falsch
     */
    public static String validatePhoneNumber(String phoneNumber) {
        return validateStringViaRegex(phoneNumber, ValidationRegexEnum.PHONE_NUMBER.getRegex(), "Die Telefonnummer darf nicht leer sein.");
    }


    /**
     * Prüft, ob eine Führerscheinnummer gültig ist.
     * Es sind nur deutsche Führerscheinnummer erlaubt.
     *
     * @param driverLicenseNumber Führerscheinnummer, die überprüft werden soll
     * @return Geprüfte Führerscheinnummer
     * @throws IllegalArgumentException Wenn die Führerscheinnummer ungültig ist
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
     * Prüft das Ablaufdatum des Führerscheins
     *
     * @param date Führerscheinablaufdatum
     * @return getrimmtes Datum
     */
    public static Calendar validateExpirationDate(Calendar date) {
        return validateDatePastForbidden(date);
    }


    /**
     * Hausnummer prüfen mit Fehlermeldung mitgeben
     *
     * @param houseNumber
     * @param errorMessage
     * @return getrimmte Hausnummer
     * @throws IllegalArgumentException wenn Hausnummer ungültig
     */
    public static String validateHouseNumber(String houseNumber, String errorMessage) {
        return validateStringViaRegex(houseNumber, ValidationRegexEnum.HOUSE_NUMBER.getRegex(), errorMessage);
    }

    /**
     * Hausnummer prüfen
     *
     * @param houseNumber
     * @return getrimmte Hausnummer
     * @throws IllegalArgumentException wenn Hausnummer ungültig
     */
    public static String validateHouseNumber(String houseNumber) {
        return validateStringViaRegex(houseNumber, ValidationRegexEnum.HOUSE_NUMBER.getRegex(), "Die Hausnummer ist ungültig");
    }


    /**
     * Postleitzahl prüfen mit Fehlermeldung
     *
     * @param errorMessage Fehlermedlung
     * @param zipCode PLZ
     * @return getrimmte PLZ
     * @throws IllegalArgumentException wenn PLZ ungültig
     */
    public static String validateZipCode(String zipCode, String errorMessage){
        return validateStringViaRegex(zipCode, ValidationRegexEnum.GERMAN_ZIP_CODE.getRegex(), errorMessage);
    }

    /**
     * Postleitzahl prüfen
     *
     * @param zipCode PLZ
     * @return getrimmte PLZ
     * @throws IllegalArgumentException wenn PLZ ungültig
     */
    public static String validateZipCode(String zipCode){
        return validateStringViaRegex(zipCode, ValidationRegexEnum.GERMAN_ZIP_CODE.getRegex(), "Die Postleitzahl ist ungültig");
    }




}
