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
    Date getStartDate();
    Date getReturnDate();
    Date getRealReturnDate();

    /* SETTER */
    void setBookingId(int bookingId);
    void setCarId(int carId);
    void setCustomerId(int customerId);
    void getStartDate(Date startDate);
    void getReturnDate(Date returnDate);
    void getRealReturnDate(Date realReturnDate);

    /* toString */
    String toString();
}
