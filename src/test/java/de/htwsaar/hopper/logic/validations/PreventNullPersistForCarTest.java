package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;


public class PreventNullPersistForCarTest {
    private PreventNullPersistForCar preventNullPersist;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private Calendar calendar;
    private Car car;

    @Before
    public void initializeValues() {
        preventNullPersist = new PreventNullPersistForCar();

        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -3);

        car = new Car();
    }

    @After
    public  void closePersistenceUnit(){
        entityManager.close();
        entityManagerFactory.close();
    }

    public void setUpPersistenceUnit(Car car){
        try{
            entityTransaction.begin();

            entityManager.persist(car);
            entityTransaction.commit();

        } finally {
            if(entityTransaction.isActive()){
                entityTransaction.rollback();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithAllValuesNullThrowsException(){
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCarTypeNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCarBrandNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCreationDateNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithSeatsNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(0);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithBasePriceNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(0);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCurrentPriceNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(0);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithLicensePlateNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate(null);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test
    public void persistWithModelNotNullThrowsNoException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test
    public void persistWithAllValuesCorrect(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(10);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Alpha");
        preventNullPersist.testAttributesOnNull(car);
        setUpPersistenceUnit(car);
    }

}