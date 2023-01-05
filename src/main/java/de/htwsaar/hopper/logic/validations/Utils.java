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

    /**
     * Prüft, ob ein String leer ist.
     * @param string String, der überprüft werden soll
     * @param message Nachricht, die bei fehlgeschlagener Prüfung ausgegeben werden soll
     * @return Getrimmter String, falls gültig
     * @throws IllegalArgumentException Falls der String null ist oder leer ist
     */
    public static String validateString(String string, String message) {
        if(string == null) {
            throw new IllegalArgumentException(message);
        }
        string = string.trim();
        if(string.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return string;
    }
}
