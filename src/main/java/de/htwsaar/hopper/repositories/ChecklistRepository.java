package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Checklist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Repository-Klasse für Checklist.
 */
public final class ChecklistRepository {

    /**
     * Findet eine Checklist über ihre ID.
     *
     * @param checklistId ID der zu findenden Checklist
     * @return Gefundene Checklist; null, falls nicht gefunden
     */
    public static Checklist find(int checklistId) {
        return (Checklist) DBObjectRepository.find(Checklist.class, checklistId);
    }

    /**
     * Holt das zuletzt in der DB gespeicherte Checklist Objekt.
     *
     * @return Die zuletzt gespeicherte Checklist
     */
    public static Checklist findLastChecklist() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            List<Checklist> checklistList = entityManager.createQuery("SELECT C FROM Checklist AS C ORDER BY C.checklistId DESC").setMaxResults(1).getResultList();
            if (checklistList.size() == 0) {
                return null;
            }
            return checklistList.get(checklistList.size() - 1);
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


    /**
     * Nimmt eine Checklist entgegen und löscht diese aus der DB.
     * Wird diese Checklist nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     *
     * @param checklist Die übergebene / zu löschende Entität.
     * @throws IllegalArgumentException Wenn Objekt nicht in DB
     */
    public static void delete(Checklist checklist) {
        DBObjectRepository.delete(checklist);
    }

    /**
     * Nimmt ein Checklist-Objekt entgegen und persistiert es in der Datenbank.
     *
     * @param checklist Das übergebene Objekt.
     */
    public static void persist(Checklist checklist) {
        DBObjectRepository.persist(checklist);
    }
}
