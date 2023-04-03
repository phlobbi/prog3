package de.htwsaar.hopper.logic.validations;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Diese Klasse enthält Methoden, die zwar nützlich sind, aber nicht in die Validierung gehören.
 */
public class Utils {
    /**
     * Prüft, ob eine Bedingung erfüllt ist.
     *
     * @param bedingung Bedingung, die überprüft werden soll
     * @param msg       Nachricht, die bei fehlgeschlagener Prüfung mit der Exception ausgegeben werden soll
     * @throws IllegalArgumentException Falls die Bedingung nicht erfüllt ist
     */
    public static void check(boolean bedingung, String msg) {
        if (!bedingung) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Berechnet die Anzahl der Tage zwischen zwei Kalenderdaten.
     * Die Methode berücksichtigt dabei, dass das Enddatum inklusive ist.
     * Beispiel: 01.01.2020 - 03.01.2020 = 3 Tage
     *
     * @param start Startdatum
     * @param end   Enddatum
     * @return Anzahl der Tage zwischen den beiden Kalenderdaten
     */
    public static int calculateDaysBetween(Calendar start, Calendar end) {
        clearHourMinuteSecond(start);
        clearHourMinuteSecond(end);
        if (start.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {
            int endDay = end.get(Calendar.DAY_OF_YEAR);
            int startDay = start.get(Calendar.DAY_OF_YEAR);
            return endDay - startDay;
        }
        if (start.get(Calendar.YEAR) < end.get(Calendar.YEAR)) {
            int yearDifference = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
            int daysBetween;
            int daysBeforeYearChange = 365 - start.get(Calendar.DAY_OF_YEAR);
            int daysAfterYearChange = end.get(Calendar.DAY_OF_YEAR);
            daysBetween = daysBeforeYearChange + daysAfterYearChange;

            while (yearDifference > 1) {
                daysBetween += 365;
                yearDifference--;
            }

            return daysBetween;
        } else {
            return (int) (ChronoUnit.DAYS.between(start.toInstant(), end.toInstant()));
        }
    }

    /**
     * Setzt die Stunden, Minuten und Sekunden eines Kalenderdatums auf 0.
     *
     * @param date Kalenderdatum, das bereinigt werden soll
     */
    public static void clearHourMinuteSecond(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Prüft, ob zwei Calendar-Objekte das gleiche Datum darstellen.
     *
     * @param date1 Kalenderdatum 1
     * @param date2 Kalenderdatum 2
     * @return true, wenn die beiden Kalenderdaten das gleiche Datum darstellen, sonst false
     */
    public static boolean isSameDate(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.DAY_OF_YEAR) == date2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Wandelt das Kalenderdatum + Uhrzeit in String um.
     *
     * @param date Kalenderdatum
     * @return Formatierter String mit Datum und Uhrzeit
     */
    public static String calendarToString(Calendar date) {
        return String.format("%02d.%02d.%04d, %02d:%02d", date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH) + 1, date.get(Calendar.YEAR), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
    }

}
