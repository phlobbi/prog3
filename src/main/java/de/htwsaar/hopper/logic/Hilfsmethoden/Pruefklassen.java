package de.htwsaar.hopper.logic.Hilfsmethoden;
import nl.garvelink.iban.IBAN;

public class Pruefklassen {
    // Objekt vom Typ "IBAN"
    private IBAN iban;
    private String phoneNumber;
    private int driveLiscenceNumber;
    //String muss mit entweder +49 oder 0 starten, darauf muss maximal 1 / folgen, anschließend eine Folge von
    //natürlichen Zahlen die mindestens aus 11 und maximal aus 15 Ziffern besteht.
    String regexTelefonnummer = "^([+]+[4]+[9]|[0])+([/]{1}){1}+[0-9]{11,15}$";

    //public void validateIBAN(IBAN iban) {
        //Utils.check(IBAN.parse(iban) == false),
          //      "Die IBAN ist ungültig!";
    //} Vermutlich obsolet da die IBAN-Klasse schon alles abfängt.

    public void validatePhonenumber(String telefonnummer) {
            Utils.check(telefonnummer == null || telefonnummer.trim().isEmpty(),
                "Die Telefonnummer darf nicht leer sein!");
            Utils.check(!telefonnummer.matches(regexTelefonnummer),
                    "Die Telefonnummer muss mit entweder 01 oder +49 beginnen und muss aus maximal 15 Zeichen bestehen!");
    }

}