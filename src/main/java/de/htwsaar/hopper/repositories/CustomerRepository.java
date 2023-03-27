package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Repository-Klasse für den Customer. Dient zum Abrufbarmachen über die Datenbank.
 *
 * @author Ronny
 */
public class CustomerRepository {

    /**
     * Findet einen speziellen Customer über seine ID.
     *
     * @param customerId ID des zu findenden Customers
     * @return Der gefundene Customer; null, falls nicht gefunden
     */
    public static Customer find(int customerId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.find(Customer.class, customerId);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten Customer durch und gibt sie als Liste zurück.
     *
     * @return Alle Customer in der Datenbank; null, falls keine existieren.
     */
    public static List<Customer> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT c FROM Customer AS c");

        try {
            return query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Sucht alle Customers, die keine Buchung am Laufen haben.
     *
     * @return Die Customer-Liste; null, falls keine verfuegbaren Customer existieren
     */
    public static List<Customer> findAvailable() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query queryForAvailableCustomer = entityManager.createQuery("SELECT c FROM Customer AS c WHERE " +
                "NOT EXISTS (SELECT b FROM Booking AS b WHERE c.customerId=b.customerId AND b.realDropOffDate = null)");

        try {
            return (List<Customer>) queryForAvailableCustomer.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Nimmt einen Customer entgegen und loescht diesen aus der DB.
     * Wird dieser Customer nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * Nach dem Löschen werden ggf. vorhandene orphaned records entfernt.
     *
     * @param customer Die übergebene / zu löschende Entität.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Customer customer) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(customer) ? customer : entityManager.merge(customer));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        removeOrphan(customer);
    }

    /**
     * Nimmt ein Customer-Objekt entgegen und persistiert es in der Datenbank.
     *
     * @param customer Das übergebene Objekt.
     */
    public static void persist(Customer customer) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(entityManager.contains(customer) ? customer : entityManager.merge(customer));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Wird nach dem Löschen eines Customers automatisch aufgerufen und durchsucht alle vorhandenen Bookings.
     * Taucht der gelöschte Customer in einem Booking auf, wird auch das korrespondierende Booking entfernt.
     *
     * @param customer Der gelöschte Customer.
     */
    private static void removeOrphan(Customer customer) {
        List<Booking> bookings = BookingRepository.findAll();

        if (bookings != null) {
            for (Booking booking : bookings) {
                if (booking.getCustomerId() == customer.getCustomerId()) {
                    BookingRepository.delete(booking);
                }
            }
        }
    }
}
