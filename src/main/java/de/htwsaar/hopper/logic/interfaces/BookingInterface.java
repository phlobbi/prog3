package de.htwsaar.hopper.logic.interfaces;

import java.util.Date;

/**
 * Interface für die Klasse Booking.
 * @author Philip
 */
public interface BookingInterface {

    /* GETTER */
    int getBookingId();
    int getCarId();
    int getCustomerId();
    Date getPickUpDate();
    Date getDropOffDate();
    Date getRealDropOffDate();

    /* SETTER */
    void setBookingId(int bookingId);
    void setCarId(int carId);
    void setCustomerId(int customerId);
    void setPickUpDate(Date startDate);
    void setDropOffDate(Date returnDate);
    void setRealDropOffDate(Date realReturnDate);

    /* toString */
    String toString();
}
