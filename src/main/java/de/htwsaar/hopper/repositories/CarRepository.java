package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Car;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CarRepository {
    /**
     * Findet ein spezielles Car ueber seine ID
     * @param carId Die gesuchte ID
     * @return Das gefundene Car; wenn keiner gefunden wurde null
     */
    public static Car find(int carId) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Car car = entityManager.find(Car.class, carId);

            if (car == null) {
                return null;
            } else {
                return car;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Geht alle gespeicherten Cars durch und gibt sie als Liste zurueck
     * @return Alle Cars in der Datenbank; null wenn (noch) keins existiert.
     */
    public static List<Car> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT c FROM Car AS c");

        try {
            List<Car> carList = query.getResultList();

            if (carList == null) {
                return null;
            } else {
                return carList;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Sucht alle Cars, die noch verfuegbar sind und gibt sie als Liste aus.
     * todo: !Workaround-Loesung; geht bestimmt mit einem einzigen Query eleganter
     * @return Die Car-Liste, null wenn keins mehr verfuegbar ist.
     */
    public static List<Car> findAvailable() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query queryForUnavailableCars = entityManager.createQuery("SELECT c FROM Car AS c, Booking AS b WHERE c.carId = b.carId");
        Query queryForAllCars = entityManager.createQuery("SELECT c FROM Car AS c");

        try {
            List<Car> listOfAll = queryForAllCars.getResultList();
            List<Car> listOfUnavailable = queryForUnavailableCars.getResultList();
            listOfAll.removeAll(listOfUnavailable);

            if (listOfAll == null) {
                return null;
            } else {
                return listOfAll;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    /**
     * Sucht alle Cars, die nicht mehr verfuegbar sind und gibt sie als Liste aus.
     * @return Die Car-Liste, null wenn alle verfuegbar sind.
     */
    public static List<Car> findUnavailable() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT c FROM Car AS c, Booking AS b WHERE c.carId = b.carId");


        try {
            List<Car> carList = query.getResultList();

            if (carList == null) {
                return null;
            } else {
                return carList;
            }
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
