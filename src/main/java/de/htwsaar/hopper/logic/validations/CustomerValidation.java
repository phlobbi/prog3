package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;
import nl.garvelink.iban.IBAN;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

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

    public static String validateHouseNumber(String houseNumber) {
        return validateStringViaRegex(houseNumber, ValidationRegexEnum.HOUSE_NUMBER.getRegex(), "Die Hausnummer ist ungültig!");
    }


}
