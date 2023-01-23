package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Calendar;


public class PreventNullPersistForBookingTest {
    private static PreventNullPersistForBooking preventNullPersist;
    private static Calendar pickUpDate;
    private static Calendar dropOffDate;
    private static Calendar realDropOffDate;
    private static Booking booking;

    @BeforeClass
    public static void setUpClass() {
        preventNullPersist = new PreventNullPersistForBooking();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        pickUpDate = calendar;
        dropOffDate = calendar;
        realDropOffDate = calendar;
    }

    @Before
    public void setUpBooking() {
        booking = new Booking();
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
        BookingRepository.persist(booking);
    }

}