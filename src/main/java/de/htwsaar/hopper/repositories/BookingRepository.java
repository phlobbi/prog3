package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Checklist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository-Klasse für das Booking. Dient zum Abrufbarmachen über die Datenbank.
 *
 * @author Ronny
 */
public class BookingRepository {

    /**
     * Findet ein Booking über seine ID.
     *
     * @param bookingId ID des zu findenden Bookings
     * @return Das gefundene Booking; null, falls nicht gefunden
     */
    public static Booking find(int bookingId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.find(Booking.class, bookingId);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten Bookings durch und gibt sie als Liste zurück.
     *
     * @return Alle Bookings in der Datenbank; null, falls keine existieren.
     */
    public static List<Booking> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT b FROM Booking AS b");

        try {
            return (List<Booking>) query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }


    }

    /**
     * Nimmt ein Booking entgegen und loescht dieses aus der DB.
     * Wird dieses Booking nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     *
     * @param booking Die uebergebene / zu loeschende Entitaet.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Booking booking) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(booking) ? booking : entityManager.merge(booking));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        removeOrphan(booking);
    }

    /**
     * Nimmt ein Booking-Objekt entgegen und persistiert es in der Datenbank.
     *
     * @param booking Das uebergebene Objekt.
     */
    public static void persist(Booking booking) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(entityManager.contains(booking) ? booking : entityManager.merge(booking));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Wird nach dem Löschen eines Bookings automatisch aufgerufen und
     * löscht – wenn vorhanden – die zugeordnete Checklist.
     *
     * @param booking Booking-Instanz, dessen Checklist gelöscht werden soll
     */
    private static void removeOrphan(Booking booking) {
        Checklist checklist = ChecklistRepository.find(booking.getChecklistId());
        if (checklist != null)
            ChecklistRepository.delete(checklist);
    }

    /**
     * Gibt als Liste alle Buchungen, die noch nicht abgeschlossen sind (wo realDropOffDate ist null)
     *
     * @return Liste der nicht-abgeschlossenen Buchungen
     */
    public static List<Booking> findUncompleted(){
        List<Booking> uncompletedBookings = new ArrayList<>();
        List<Booking> bookingsList = findAll();

        for(Booking booking : bookingsList){
            if(booking.getRealDropOffDate() == null) {
                uncompletedBookings.add(booking);
            }
        }
        return uncompletedBookings;
    }

    /**
     * Gibt als Liste alle Buchungen, die abgeschlossen sind (wo realDropOffDate ist nicht null)
     *
     * @return Liste der abgeschlossenen Buchungen
     */
    public static List<Booking> findCompleted(){
        List<Booking> completedBookings = new ArrayList<>();
        List<Booking> bookingsList = findAll();

        for(Booking booking : bookingsList){
            if(booking.getRealDropOffDate() != null) {
                completedBookings.add(booking);
            }
        }
        return completedBookings;
    }
}
