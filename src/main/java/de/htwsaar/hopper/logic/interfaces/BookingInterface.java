package de.htwsaar.hopper.logic.interfaces;

import java.util.Calendar;

/**
 * Interface für die Klasse Booking.
 * @author Philip
 */
public interface BookingInterface {

    /* GETTER */
    int getBookingId();
    int getCarId();
    int getCustomerId();
    Calendar getPickUpDate();
    Calendar getDropOffDate();
    Calendar getRealDropOffDate();

    /* SETTER */
    void setCarId(int carId);
    void setCustomerId(int customerId);
    void setPickUpDate(Calendar startDate);
    void setDropOffDate(Calendar returnDate);
    void setRealDropOffDate(Calendar realReturnDate);

    /* toString */
    String toString();

    double calculatePrice(int carId);

    double calculateFinalPrice(int carId);
}
