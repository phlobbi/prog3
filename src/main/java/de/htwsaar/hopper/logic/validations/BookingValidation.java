package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import de.htwsaar.hopper.repositories.BookingRepository;
import java.util.Calendar;
import java.util.List;

public class BookingValidation extends Validation {

    /**
     * Prüft, ob die CarId existiert und ob das Auto bereits vergeben ist
     *
     * @param carId Die zu prüfende CarId
     * @return carId, falls gültig
     * @throws IllegalArgumentException Falls die carId nicht existiert
     */
    public static int validateCarId(int carId) {
        Utils.check(CarRepository.find(carId) != null, "CarId existiert nicht");
        List<Booking> bookings = BookingRepository.findAll();
        for (Booking booking : bookings) {
            Utils.check(booking.getRealDropOffDate() != null && booking.getCarId() != carId, "Das Auto ist bereits vergeben");
        }
        return carId;
    }

    /**
     * Prüft, ob die CustomerId existiert und ob der Kunde bereits ein Auto gebucht hat
     *
     * @param customerId Die zu prüfende CustomerId
     * @return customerId, falls gültig
     * @throws IllegalArgumentException Falls die customerId nicht existiert
     */
    public static int validateCustomerId(int customerId) {
        Utils.check(CustomerRepository.find(customerId) != null, "CustomerId existiert nicht");
        List<Booking> bookings = BookingRepository.findAll();
        for (Booking booking : bookings) {
            Utils.check(booking.getRealDropOffDate() != null && booking.getCustomerId() != customerId, "Der Kunde hat bereits ein Auto gebucht");
        }
        return customerId;
    }

    /**
     * Prüft, ob das PickUpDate in der Zukunft liegt
     *
     * @param pickUpDate Das zu prüfende PickUpDate
     * @return pickUpDate, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Zukunft liegt
     */
    public static Calendar validatePickUpDate(Calendar pickUpDate) {
        return validateDateFutureForbidden(pickUpDate);
    }

    /**
     * Prüft, ob das DropOffDate in der Vergangenheit liegt
     *
     * @param dropOffDate Das zu prüfende DropOffDate
     * @return dropOffDate, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     */
    public static Calendar validateDropOffDate(Calendar dropOffDate) {
        return validateDatePastForbidden(dropOffDate);
    }

    /**
     * Prüft, ob das PickUpDate vor dem DropOffDate liegt
     * @param pickUpDate Abholtermin
     * @param dropOffDate Abgabetermin
     * @throws IllegalArgumentException Falls das PickUpDate nach dem DropOffDate liegt
     */
    public static void validatePickUpDateBeforeDropOffDate(Calendar pickUpDate, Calendar dropOffDate) {
        if(!pickUpDate.before(dropOffDate)){
            throw new IllegalArgumentException("Abholtermin liegt nach Abgabetermin.");
        }
    }

}
