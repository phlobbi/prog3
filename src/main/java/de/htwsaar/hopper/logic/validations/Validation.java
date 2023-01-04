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

    public static void validateEmail(String email) {
        Utils.check(email == null || email.trim().isEmpty(),
                "Die Email darf nicht leer sein!");
       Utils.check(!email.matches(regexEmail),
            "Die Email ist ungültig!");
    }
    public static void validatePhonenumber(String telefonnummer) {
            Utils.check(telefonnummer == null || telefonnummer.trim().isEmpty(),
                "Die Telefonnummer darf nicht leer sein!");
            Utils.check(!telefonnummer.matches(regexTelefonnummer),
                    "Die Telefonnummer muss mit entweder 01 oder +49 beginnen und muss aus maximal 15 Zeichen bestehen!");
    }

}