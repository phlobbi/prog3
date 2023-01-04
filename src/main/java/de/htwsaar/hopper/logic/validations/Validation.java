package de.htwsaar.hopper.logic.validations;
import nl.garvelink.iban.IBAN;

public class Validation {
    // Objekt vom Typ "IBAN"
    private IBAN iban;
    private String email;
    private String phoneNumber;
    private int driverLicenseNumber;

    //TODO Email-Regex optimieren
    final static String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    final static String regexTelefonnummer = "^(\\+49|0)[0-9]{1,5}\\/?[0-9]{4,10}";

    //public void validateIBAN(IBAN iban) {
        //Utils.check(IBAN.parse(iban) == false),
          //      "Die IBAN ist ungültig!";
    // Vermutlich obsolet da die IBAN-Klasse schon alles abfängt.

    /**
     * Prüft, ob eine E-Mail gültig ist.
     * @param email E-Mail, die überprüft werden soll
     * @return Getrimmte E-Mail, falls gültig
     */
    public static String validateEmail(String email) {
        email = Utils.validateString(email, "Die Email darf nicht leer sein!");
        Utils.check(email.matches(regexEmail),
            "Die Email ist ungültig!");
        return email.trim();
    }

    /**
     * Prüft, ob eine Telefonnummer gültig ist.
     * Es sind nur deutsche Telefonnummern erlaubt.
     * @param telefonnummer Telefonnummer, die überprüft werden soll
     * @return Getrimmte Telefonnummer, falls gültig
     */
    public static String validatePhonenumber(String telefonnummer) {
            telefonnummer = Utils.validateString(telefonnummer, "Die Telefonnummer darf nicht leer sein!");
            Utils.check(telefonnummer.matches(regexTelefonnummer),
                    "Die Telefonnummer ist ungültig!");
            return telefonnummer.trim();
    }
}