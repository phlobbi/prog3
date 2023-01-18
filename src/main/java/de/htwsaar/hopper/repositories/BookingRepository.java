package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
     * @param booking Die uebergebene / zu loeschende Entitaet.
     */
    public static void delete(Booking booking) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(booking) ? booking : entityManager.merge(booking));

            entityManager.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            System.out.println("Das zu loeschende Objekt existiert nicht in der Datenbank.");
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
