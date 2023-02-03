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
     * Nimmt ein Checklist-Objekt entgegen und persistiert es in der Datenbank.
     * @param checklist Das übergebene Objekt.
     */
    public static void persist(Checklist checklist) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(checklist);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
