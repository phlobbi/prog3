package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class BookingRepository {
    /**
     * Findet ein spezielles Booking ueber seine ID
     * @param bookingId Die gesuchte ID
     * @return Das gefundene Booking; wenn keines gefunden wurde null
     */
    public static Booking find(int bookingId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Booking booking = entityManager.find(Booking.class, bookingId);

            if (booking == null) {
                return null;
            } else {
                return booking;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten Bookings durch und gibt sie als Liste zurueck
     * @return Alle Bookings in der Datenbank; null wenn (noch) keines existiert.
     */
    public static List<Booking> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT b FROM Booking AS b");

        try {
            List<Booking> bookingList = query.getResultList();

            if (bookingList == null) {
                return null;
            } else {
                return bookingList;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
