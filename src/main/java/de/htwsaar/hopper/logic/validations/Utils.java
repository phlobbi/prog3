package de.htwsaar.hopper.logic.validations;

/**
 * Diese Klasse enthält Methoden, die zwar nützlich sind, aber nicht in die Validierung gehören.
 */
public class Utils {
    /**
     * Prüft, ob eine Bedingung erfüllt ist.
     * @param bedingung Bedingung, die überprüft werden soll
     * @param msg Nachricht, die bei fehlgeschlagener Prüfung mit der Exception ausgegeben werden soll
     * @throws IllegalArgumentException Falls die Bedingung nicht erfüllt ist
     */
    public static void check(boolean bedingung, String msg) {
        if(!bedingung)  {
            throw new IllegalArgumentException(msg);
        }
    }

}
