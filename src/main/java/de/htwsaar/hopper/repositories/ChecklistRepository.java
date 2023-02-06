package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Checklist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ChecklistRepository {

    /**
     * Findet eine Checklist über ihre ID.
     * @param checklistId ID der zu findenden Checklist
     * @return Gefundene Checklist; null, falls nicht gefunden
     */
    public static Checklist find(int checklistId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.find(Checklist.class, checklistId);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Nimmt eine Checklist entgegen und loescht diese aus der DB.
     * Wird diese Checklist nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * @param checklist Die uebergebene / zu loeschende Entitaet.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Checklist checklist) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            entityManager.remove(entityManager.contains(checklist) ? checklist : entityManager.merge(checklist));

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Nimmt ein Checklist-Objekt entgegen und persistiert es in der Datenbank.
     * @param checklist Das übergebene Objekt.
     */
    public static void persist(Checklist checklist) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.contains(checklist) ? checklist : entityManager.merge(checklist));
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
