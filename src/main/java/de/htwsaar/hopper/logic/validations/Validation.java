package de.htwsaar.hopper.logic.validations;

import java.util.Calendar;

public class Validation {
    final static String regexTelefonnummer = "^(\\+49|0)[0-9]{1,5}(\\/| )?[0-9]{4,10}";
    final static String regexDriverLicenseNumber = "^[A-z0-9][0-9]{2}[A-z0-9]{6}[0-9]{1}[A-z0-9]{1}";
    final static String regexLicensePlate = "^[A-ZÖÜÄ]{1,3}-[A-ZÖÜÄ]{1,2}-[1-9]{1}[0-9]{1,3}";
    final static String regexHouseNumber = "^[0-9]{1,3}[A-z]{0,1}";
    final static String regexGermanZipCode = "^(0[1-9]\\d{3}|[1-9]\\d{4})$";


    /**
     * Prüft, ob ein String leer ist.
     *
     * @param string  String, der überprüft werden soll
     * @param message Nachricht, die bei fehlgeschlagener Prüfung ausgegeben werden soll
     * @return Getrimmter String, falls gültig
     * @throws IllegalArgumentException Falls der String null ist oder leer ist
     */
    public static String validateString(String string, String message) {
        if (string == null) {
            throw new IllegalArgumentException(message);
        }
        string = string.trim();
        if (string.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return string;
    }

    /**
     * Prüft, ob ein String einem Regex entspricht
     *
     * @param string Eingabestring
     * @param regex Regex, mit dem der String verglichen wird
     * @param message Nachricht bei Nichtübereinstimmung mit Regex
     * @return Getrimmter String, falls gültig
     */
    public static String validateStringViaRegex(String string, String regex, String message){
        if (string == null)
            throw new IllegalArgumentException("Der String darf nicht leer sein");

        string = string.trim();

        if (string.isEmpty())
            throw new IllegalArgumentException("Der String darf nicht leer sein");

        Utils.check(string.matches(regex), message);

        return string;
    }

    /**
     * Prüft, ob eine Telefonnummer gültig ist.
     * Es sind nur deutsche Telefonnummern erlaubt.
     *
     * @param telefonnummer Telefonnummer, die überprüft werden soll
     * @return Getrimmte Telefonnummer, falls gültig
     * @throws IllegalArgumentException Wenn die Telefonnummer ungültig ist
     */
    public static String validatePhonenumber(String telefonnummer) {
        telefonnummer = validateString(telefonnummer, "Die Telefonnummer darf nicht leer sein!");
        Utils.check(telefonnummer.matches(regexTelefonnummer),
                "Die Telefonnummer ist ungültig!");
        return telefonnummer;
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
        Utils.check(driverLicenseNumber.matches(regexDriverLicenseNumber),
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

    public static int validateSeats(int seats) {
        Utils.check(seats > 0, "Die Anzahl der Sitze muss größer als 0 sein!");
        Utils.check(seats < 302, "Die Anzahl der Sitze darf nicht größer als 301 sein!\n" +
                "Der Volvo Gran Artic 300 mit 301 Sitzen ist der größte Bus der Welt!"); //Schleichwerbung? rausnehmen? :D
        return seats;
    }

    public static Calendar validateDate(Calendar date) {
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();
        if (date.before(currentCalendar)) {
            return date;
        } else {
            throw new IllegalArgumentException("Das Datum darf nicht in der Zukunft liegen!");
        }
    }

    public static double validateBasePrice(double basePrice) {
        Utils.check(basePrice > 0.00, "Der Basispreis muss größer als 0 sein!");
        return basePrice;
    }

    public static double validateCurrentPrice(double currentPrice) {
        Utils.check(currentPrice > 0.00, "Der aktuelle Preis muss größer als 0 sein!");
        return currentPrice;
    }

    public static String validateLicensePlate(String licensePlate) {
        licensePlate = validateString(licensePlate, "Die Kennzeichen dürfen nicht leer sein!");
        licensePlate = licensePlate.toUpperCase();
        Utils.check(licensePlate.matches(regexLicensePlate),
                "Die Kennzeichen sind ungültig!");
        return licensePlate;
    }

    public static String validateHouseNumber(String houseNumber) {
        houseNumber = validateString(houseNumber, "Die Hausnummer darf nicht leer sein!");
        Utils.check(houseNumber.matches(regexHouseNumber),
                "Die Hausnummer ist ungültig!");
        return houseNumber;
    }

    public static String validateZipCode(String zipCode) {
        zipCode = validateString(zipCode, "Die Postleitzahl darf nicht leer sein!");
        Utils.check(zipCode.matches(regexGermanZipCode),
                "Die Postleitzahl ist ungültig!");
        return zipCode;
    }

}
