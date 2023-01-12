package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Repository-Klasse fuer den Customer. Dient zum Abrufbarmachen ueber die Datenbank.
 * @author roblin
 */
public class CustomerRepository {

    /**
     * Findet einen speziellen Customer ueber seine ID.
     * @param customerId Die gesuchte ID.
     * @return Der gefundene Customer; wenn keiner gefunden wurde null.
     */
    public static Customer find(int customerId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Customer customer = entityManager.find(Customer.class, customerId);

            if (customer == null) {
                return null;
            } else {
                return customer;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten Customer durch und gibt sie als Liste zurueck.
     * @return Alle Customer in der Datenbank; null wenn (noch) keiner existiert.
     */
    public static List<Customer> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT c FROM Customer AS c");

        try {
            List<Customer> customerList = query.getResultList();

            if (customerList == null) {
                return null;
            } else {
                return customerList;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
