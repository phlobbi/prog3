package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;


public class PreventNullPersistForBookingTest {
    private PreventNullPersistForBooking preventNullPersist;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private Calendar pickUpDate;
    private Calendar dropOffDate;
    private Calendar realDropOffDate;
    private Booking booking;

    @Before
    public void initializeValues() {
        preventNullPersist = new PreventNullPersistForBooking();

        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        pickUpDate = calendar;
        dropOffDate = calendar;
        realDropOffDate = calendar;

        booking = new Booking();
    }

    @After
    public  void closePersistenceUnit(){
        entityManager.close();
        entityManagerFactory.close();
    }
    public void setUpPersistenceUnit(Booking booking) {
        try {
            entityTransaction.begin();

            entityManager.persist(booking);
            entityTransaction.commit();

        } finally {
            if(entityTransaction.isActive()){
                entityTransaction.rollback();
            }
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithAllValuesNullThrowsException(){
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCarIDSetNotNullThrowsException(){
        booking.setCarId(5);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCustomerIDSetNotNullThrowsException(){
        booking.setCarId(5);
        booking.setCustomerId(6);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithPickUpDateSetNotNullThrowsException(){
        booking.setCarId(5);
        booking.setCustomerId(6);
        booking.setPickUpDate(pickUpDate);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test
    public void persistWithDropOffDateSetNotNullThrowsNoException(){
        booking.setCarId(5);
        booking.setCustomerId(6);
        booking.setPickUpDate(pickUpDate);
        booking.setDropOffDate(dropOffDate);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test
    public void persistWithRealDropOffDateSetNotNullThrowsNoException(){
        booking.setCarId(5);
        booking.setCustomerId(6);
        booking.setPickUpDate(pickUpDate);
        booking.setDropOffDate(dropOffDate);
        booking.setRealDropOffDate(realDropOffDate);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test
    public void persistWithAllValuesCorrect(){
        booking.setCarId(5);
        booking.setCustomerId(6);
        booking.setPickUpDate(pickUpDate);
        booking.setDropOffDate(dropOffDate);
        booking.setRealDropOffDate(realDropOffDate);
        preventNullPersist.testAttributesOnNull(booking);
        setUpPersistenceUnit(booking);
    }

}