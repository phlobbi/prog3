package de.htwsaar.hopper.logic.validations;

public class Utils {
    /**
     * Prüft, ob eine Bedingung erfüllt ist. Falls nicht, wird eine IllegalArgumentException geworfen.
     * @param bedingung Bedingung, die überprüft werden soll
     * @param msg Nachricht, die bei fehlgeschlagener Prüfung ausgegeben werden soll
     * @throws IllegalArgumentException Falls die Bedingung nicht erfüllt ist
     */
    public static void check(boolean bedingung, String msg) {
        if(!bedingung)  {
            throw new IllegalArgumentException(msg);
        }
    }
}
