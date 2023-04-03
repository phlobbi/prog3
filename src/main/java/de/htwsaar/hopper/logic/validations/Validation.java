package de.htwsaar.hopper.logic.validations;

import java.util.Calendar;

/**
 * Diese abstrakte Klasse enthält Methoden, die in allen Validierungen verwendet werden können.
 */
public abstract class Validation {

    /**
     * Prüft, ob ein String leer oder null ist.
     * Der String wird ebenfalls getrimmt.
     *
     * @param string  String, der überprüft werden soll
     * @param message Nachricht, die bei fehlgeschlagener Prüfung ausgegeben werden soll
     * @return Getrimmter String, falls gültig
     * @throws IllegalArgumentException Falls der String null oder leer ist
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
     * Prüft, ob ein String einem Regex entspricht.
     * Der String darf nicht leer oder null sein.
     *
     * @param string  Eingabestring
     * @param regex   Regex, mit dem der String verglichen wird
     * @param message Nachricht bei Nichtübereinstimmung mit Regex
     * @return Getrimmter String, falls gültig
     * @throws IllegalArgumentException Falls der String nicht dem Regex entspricht
     * @throws IllegalArgumentException Falls der String null oder leer ist
     */
    public static String validateStringViaRegex(String string, String regex, String message) {
        string = validateString(string, message);

        if (string.isEmpty())
            throw new IllegalArgumentException("Der String darf nicht leer sein");

        Utils.check(string.matches(regex), message);

        return string;
    }

    /**
     * Prüft, ob ein Datum gültig ist.
     * Es darf nicht in der Vergangenheit liegen.
     *
     * @param date         Zu prüfendes Datum
     * @param errorMessage Fehlermeldung, die ausgegeben werden soll
     * @return Datum, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     * @throws IllegalArgumentException Falls das Datum null ist
     */
    public static Calendar validateDatePastForbiddenMinute(Calendar date, String errorMessage) {
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();

        validateHour(date.get(Calendar.HOUR_OF_DAY));
        validateMinute(date.get(Calendar.MINUTE));

        // Fall: Stunde gleich, Minute kleiner als jetzt
        if (date.get(Calendar.MINUTE) < (currentCalendar.get(Calendar.MINUTE))
                && date.get(Calendar.HOUR_OF_DAY) == (currentCalendar.get(Calendar.HOUR_OF_DAY))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Tag gleich, Stunde kleiner als jetzt
        if (date.get(Calendar.HOUR_OF_DAY) < (currentCalendar.get(Calendar.HOUR_OF_DAY))
                && date.get(Calendar.DAY_OF_YEAR) == (currentCalendar.get(Calendar.DAY_OF_YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Jahr gleich, Tag kleiner als jetzt
        if (date.get(Calendar.DAY_OF_YEAR) < (currentCalendar.get(Calendar.DAY_OF_YEAR))
                && date.get(Calendar.YEAR) == (currentCalendar.get(Calendar.YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Jahr kleiner als jetzt
        if (date.get(Calendar.YEAR) < (currentCalendar.get(Calendar.YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        return date;
    }

    /**
     * Hilfsmethode, die validateDatePastForbiddenMinute mit einer Fehlermeldung aufruft.
     *
     * @param date Zu prüfendes Datum
     * @return Datum, falls gültig
     */
    public static Calendar validateDatePastForbiddenMinute(Calendar date) {
        return validateDatePastForbiddenMinute(date, "Das Datum darf nicht in der Vergangenheit liegen!");
    }


    /**
     * Prüft, ob ein Datum gültig ist.
     * Es darf nicht in der Zukunft liegen.
     *
     * @param date         Zu prüfendes Datum
     * @param errorMessage Fehlermeldung, die ausgegeben werden soll
     * @return Datum, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Zukunft liegt
     * @throws IllegalArgumentException Falls das Datum null ist
     */
    public static Calendar validateDateFutureForbiddenMinute(Calendar date, String errorMessage) {
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();

        validateHour(date.get(Calendar.HOUR_OF_DAY));
        validateMinute(date.get(Calendar.MINUTE));

        // Fall: Stunde gleich, Minute später als jetzt
        if (date.get(Calendar.MINUTE) > (currentCalendar.get(Calendar.MINUTE))
                && date.get(Calendar.HOUR_OF_DAY) == (currentCalendar.get(Calendar.HOUR_OF_DAY))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Tag gleich, Stunde später als jetzt
        if (date.get(Calendar.HOUR_OF_DAY) > (currentCalendar.get(Calendar.HOUR_OF_DAY))
                && date.get(Calendar.DAY_OF_YEAR) == (currentCalendar.get(Calendar.DAY_OF_YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Jahr gleich, Tag später als jetzt
        if (date.get(Calendar.DAY_OF_YEAR) > (currentCalendar.get(Calendar.DAY_OF_YEAR))
                && date.get(Calendar.YEAR) == (currentCalendar.get(Calendar.YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Jahr später als jetzt
        if (date.get(Calendar.YEAR) > (currentCalendar.get(Calendar.YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        return date;
    }

    /**
     * Hilfsmethode, die validateDateFutureForbiddenMinute mit einer Fehlermeldung aufruft.
     *
     * @param date Zu prüfendes Datum
     * @return Datum, falls gültig
     */
    public static Calendar validateDateFutureForbiddenMinute(Calendar date) {
        return validateDatePastForbiddenMinute(date, "Das Datum darf nicht in der Vergangenheit liegen!");
    }


    /**
     * Prüft, ob ein Datum gültig ist.
     * Es darf nicht in der Vergangenheit liegen.
     *
     * @param date Zu prüfendes Datum
     * @return Datum, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     * @throws IllegalArgumentException Falls das Datum null ist
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
     * Prüft, ob ein Datum gültig ist.
     * Es darf nicht in der Zukunft liegen.
     *
     * @param date Zu prüfendes Datum
     * @return Datum, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Zukunft liegt
     * @throws IllegalArgumentException Falls das Datum null ist
     */
    public static Calendar validateDateFutureForbidden(Calendar date) {
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();
        if (date.before(currentCalendar)) {
            return date;
        } else {
            throw new IllegalArgumentException("Das Datum darf nicht in der Zukunft liegen!");
        }
    }

    /**
     * Prüft, ob eine Stunde gültig ist.
     *
     * @param hour Stunde
     * @return Stunde, falls gültig
     * @throws IllegalArgumentException Falls die Stunde nicht zwischen 0 und 23 liegt
     */
    public static int validateHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Die Stunde muss zwischen 0 und 23 liegen!");
        }
        return hour;
    }

    /**
     * Prüft, ob eine Minute gültig ist.
     *
     * @param minute Minute
     * @return Minute, falls gültig
     * @throws IllegalArgumentException Falls die Minute nicht zwischen 0 und 59 liegt
     */
    public static int validateMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Die Minute muss zwischen 0 und 59 liegen!");
        }
        return minute;

    }
}
