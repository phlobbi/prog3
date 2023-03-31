package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Checklist;
import de.htwsaar.hopper.logic.implementations.Customer;

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
        return (Checklist) DBObjectRepository.find(Checklist.class, checklistId);
    }

    /**
     * Nimmt eine Checklist entgegen und loescht diese aus der DB.
     * Wird diese Checklist nicht in der DB gefunden, wird eine IllegalArgumentException geworfen.
     * @param checklist Die uebergebene / zu loeschende Entitaet.
     * @throws IllegalArgumentException wenn Objekt nicht in DB
     */
    public static void delete(Checklist checklist) {
        DBObjectRepository.delete(checklist);
    }

    /**
     * Nimmt ein Checklist-Objekt entgegen und persistiert es in der Datenbank.
     * @param checklist Das übergebene Objekt.
     */
    public static void persist(Checklist checklist) {
        DBObjectRepository.persist(checklist);
    }
}
