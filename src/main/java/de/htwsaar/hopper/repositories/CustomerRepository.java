package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Repository-Klasse für den Customer. Dient zum Abrufbarmachen über die Datenbank.
 * @author Ronny
 */
public class CustomerRepository {

    /**
     * Findet einen speziellen Customer über seine ID.
     * @param customerId ID des zu findenden Customers
     * @return Der gefundene Customer; null, falls nicht gefunden
     */
    public static Customer find(int customerId) {
        return (Customer) DBObjectRepository.find(Customer.class, customerId);
    }

    /**
     * Geht alle gespeicherten Customer durch und gibt sie als Liste zurück.
     * @return Alle Customer in der Datenbank; null, falls keine existieren.
     */
    public static List<Customer> findAll() {
        return (List<Customer>) DBObjectRepository.findAll(Customer.class, "Customer");
    }

    /**
     * Nimmt einen Customer entgegen und loescht diesen aus der DB.
     * Wird dieser Customer nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * Nach dem Löschen werden ggf. vorhandene orphaned records entfernt.
     * @param customer Die übergebene / zu löschende Entität.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Customer customer) {
        DBObjectRepository.delete(customer);

        //todo: testen ob removeOrphan funktioniert
        removeOrphan(customer);
    }

    /**
     * Nimmt ein Customer-Objekt entgegen und persistiert es in der Datenbank.
     * @param customer Das übergebene Objekt.
     */
    public static void persist(Customer customer) {
        DBObjectRepository.persist(customer);
    }

    /**
     * Wird nach dem Löschen eines Customers automatisch aufgerufen und durchsucht alle vorhandenen Bookings.
     * Taucht der gelöschte Customer in einem Booking auf, wird auch das korrespondierende Booking entfernt.
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
