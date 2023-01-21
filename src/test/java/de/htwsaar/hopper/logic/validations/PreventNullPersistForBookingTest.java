package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import org.junit.Test;
import java.util.Calendar;


public class PreventNullPersistForBookingTest {
    private PreventNullPersistForBooking preventNullPersist;
    private Calendar pickUpDate;
    private Calendar dropOffDate;
    private Calendar realDropOffDate;
    private Booking booking;

    public PreventNullPersistForBookingTest() {
        preventNullPersist = new PreventNullPersistForBooking();
        booking = new Booking();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        pickUpDate = calendar;
        dropOffDate = calendar;
        realDropOffDate = calendar;
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