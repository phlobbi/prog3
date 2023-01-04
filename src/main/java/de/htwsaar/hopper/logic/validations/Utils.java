package de.htwsaar.hopper.logic.validations;

public class Utils {
    /**
     * Check Methode, wirft IllegalArgumentException,
     * falls eine Bedingung nicht erfüllt ist.
     * @param bedingung Bedingung, die überprüft werden soll
     * @param msg Nachricht, die bei einem Fehler ausgegeben werden soll
     */
    public static void check(boolean bedingung, String msg) {
        if(!bedingung)  {
            throw new IllegalArgumentException(msg);
        }
    }
}
