package de.htwsaar.hopper.logic.validations;
import nl.garvelink.iban.IBAN;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

public class Validation {
    // Objekt vom Typ "IBAN"
    private IBAN iban;
    private String email;
    private String phoneNumber;
    private int driverLicenseNumber;


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
        //allow Localhost mails
        boolean allowLocal = false;

        /*
        AllowTld hab ich aus Testcases geschlossen, dass es false sein muss
        Hab nämlich bei mailWithoutTldNotWorking() gesehen, dass es mit true für ne @hotmail
        einfach durchgewunken wird, daher müsste false gehen, falls euch noch Testcases einfallen,
        könnt ihr die gerne hinzufügen!
         */
        boolean allowTld =  false;

        //DomainValidator für den EmailValidator
        DomainValidator domainValidator = DomainValidator.getInstance(allowLocal);

        //Angaben ob Local und TLD erlaubt sind
        EmailValidator emailValidator = new EmailValidator(allowLocal, allowTld, domainValidator);

        //trimmen
        email = email.trim();

        //prüfen ob Mail gültig ist
        if (emailValidator.isValid(email)){
            return email;
        } else {
            throw new IllegalArgumentException("Die E-Mail ist ungültig!");
        }
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