package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Checklist;

import java.util.List;

/**
 * Repository-Klasse für das Booking. Dient zum Abrufbarmachen über die Datenbank.
 * @author Ronny
 */
public class BookingRepository {

    /**
     * Findet ein Booking über seine ID.
     * @param bookingId ID des zu findenden Bookings
     * @return Das gefundene Booking; null, falls nicht gefunden
     */
    public static Booking find(int bookingId) {
        return (Booking) DBObjectRepository.find(Booking.class, bookingId);
    }

    /**
     * Geht alle gespeicherten Bookings durch und gibt sie als Liste zurück.
     * @return Alle Bookings in der Datenbank; null, falls keine existieren.
     */
    public static List<Booking> findAll() {
        return (List<Booking>) DBObjectRepository.findAll(Booking.class,"Booking");
    }

    /**
     * Nimmt ein Booking entgegen und loescht dieses aus der DB.
     * Wird dieses Booking nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * @param booking Die uebergebene / zu loeschende Entitaet.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Booking booking) {
        DBObjectRepository.delete(booking);

        removeOrphan(booking);
    }

    /**
     * Nimmt ein Booking-Objekt entgegen und persistiert es in der Datenbank.
     * @param booking Das uebergebene Objekt.
     */
    public static void persist(Booking booking) {
        DBObjectRepository.persist(booking);
    }

    /**
     * Wird nach dem Löschen eines Bookings automatisch aufgerufen und
     * löscht – wenn vorhanden – die zugeordnete Checklist.
     * @param booking
     */
    private static void removeOrphan(Booking booking) {
        Checklist checklist = ChecklistRepository.find(booking.getChecklistId());
        if (checklist != null)
            ChecklistRepository.delete(checklist);
    }
}
