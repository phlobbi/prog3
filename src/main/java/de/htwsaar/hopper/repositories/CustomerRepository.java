package de.htwsaar.hopper.repositories;

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
     * Nimmt einen Customer entgegen und loescht diesen aus der DB.
     * Wird dieser Customer nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * @param customer Die uebergebene / zu loeschende Entitaet.
     */
    public static void delete(Customer customer) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(customer) ? customer : entityManager.merge(customer));

            entityManager.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            System.out.println("Das zu loeschende Objekt existiert nicht in der Datenbank.");
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
