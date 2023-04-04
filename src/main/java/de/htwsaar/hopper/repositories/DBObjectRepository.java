package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.interfaces.DBObjectInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Zentrales Repository, über das die Funktionen der untergeordneten Repositories ausgeführt werden.
 * Nutzt dazu generische Implementierung der Methoden sowie das übergeordnete Interface der DB-Objekte.
 *
 * @author Ronny
 */
public class DBObjectRepository {

    /**
     * Findet ein DB-Objekt über seine ID
     *
     * @param classOfSearchedEntity Klasse des gesuchten Objekts
     * @param id                    ID des zu findenden Objekts
     * @return Das gefundene Objekt; null, falls nicht gefunden
     */
    public static DBObjectInterface find(Class<? extends DBObjectInterface> classOfSearchedEntity, int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.find(classOfSearchedEntity, id);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten DB-Objekte der gegebenen Tabelle durch und gibt sie als Liste zurück.
     *
     * @param table Name der Tabelle, deren Objekte gesucht werden sollen
     * @return Alle Objekte in der Datenbank; null, falls keine existieren.
     */
    public static List<? extends DBObjectInterface> findAll(String table) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String s = String.format("SELECT x FROM %s AS x", table);
        Query query = entityManager.createQuery(s);

        try {
            return query.getResultList();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Nimmt ein DB-Objekt entgegen und löscht dieses aus der DB.
     * Wird dieses nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     *
     * @param obj Die übergebene / zu löschende Entität.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(DBObjectInterface obj) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(obj) ? obj : entityManager.merge(obj));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Nimmt ein DB-Objekt entgegen und persistiert es in der Datenbank.
     *
     * @param obj Das übergebene Objekt.
     */
    public static void persist(DBObjectInterface obj) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.persist(entityManager.contains(obj) ? obj : entityManager.merge(obj));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
