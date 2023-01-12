package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.ValidationRegexEnum;

import java.util.Calendar;

public class Validation {

    final static String regexLicensePlate = "^[A-ZÖÜÄ]{1,3}-[A-ZÖÜÄ]{1,2}-[1-9]{1}[0-9]{1,3}";



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
    public static Calendar validateDateFutureForbidden(Calendar date) {
        Utils.check(date != null, "Das Datum darf nicht leer sein!");
        Calendar currentCalendar = Calendar.getInstance();
        if (date.before(currentCalendar)) {
            return date;
        } else {
            throw new IllegalArgumentException("Das Datum darf nicht in der Zukunft liegen!");
        }
    }


    /*
        Car Methods to be moved
     */

    public static int validateSeats(int seats) {
        Utils.check(seats > 0, "Die Anzahl der Sitze muss größer als 0 sein!");
        Utils.check(seats < 302, "Die Anzahl der Sitze darf nicht größer als 301 sein!\n" +
                "Der Volvo Gran Artic 300 mit 301 Sitzen ist der größte Bus der Welt!"); //Schleichwerbung? rausnehmen? :D
        return seats;
    }







}
