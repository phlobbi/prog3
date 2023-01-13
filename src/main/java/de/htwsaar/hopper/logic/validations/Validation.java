package de.htwsaar.hopper.logic.validations;

import java.util.Calendar;

public abstract class Validation {

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
     * Prüft ein Datum, was nicht in der Vergangenheit liegen darf
     * @param date Datum
     * @return das Datum wenn es passt
     * @throws IllegalArgumentException wenn das Datum in der Vergangenheit liegt
     */
    public static Calendar validateDatePastForbidden(Calendar date) {
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();
        if (date.after(currentCalendar)) {
            return date;
        } else {
            throw new IllegalArgumentException("Das Datum darf nicht in der Vergangenheit liegen!");
        }
    }

    /**
     * Prüft ein Datum, was nicht in der Zukunft liegen darf
     * @param date Datum
     * @return das Datum wenn es passt
     * @throws IllegalArgumentException wenn das Datum in der Zukunft liegt
     */
    public static Calendar validateDateFutureForbidden(Calendar date){
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();
        if (date.before(currentCalendar)) {
            return date;
        } else {
            throw new IllegalArgumentException("Das Datum darf nicht in der Zukunft liegen!");
        }
    }

}
