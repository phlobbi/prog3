package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import de.htwsaar.hopper.repositories.BookingRepository;
import java.util.Calendar;
import java.util.List;

public class BookingValidation extends Validation {

    /**
     * Prueft ob die CarId existiert und ob das Auto bereits vergeben ist
     *
     * @param carId Die zu pruefende CarId
     * @return carId, falls gültig
     * @throws IllegalArgumentException Falls die carId nicht existiert
     */
    public static int validateCarId(int carId) {
        Utils.check(CarRepository.find(carId) != null, "CarId existiert nicht");
        List<Booking> bookings = BookingRepository.findAll();
        for (Booking booking : bookings) {
            Utils.check(booking.getCarId() != carId, "Das Auto ist bereits vergeben");
        }
        return carId;
    }

    /**
     * Prueft ob die CustomerId existiert und ob der Kunde bereits ein Auto gebucht hat
     *
     * @param customerId Die zu pruefende CustomerId
     * @return customerId, falls gültig
     * @throws IllegalArgumentException Falls die customerId nicht existiert
     */
    public static int validateCustomerId(int customerId) {
        Utils.check(CustomerRepository.find(customerId) != null, "CustomerId existiert nicht");
        List<Booking> bookings = BookingRepository.findAll();
        for (Booking booking : bookings) {
            Utils.check(booking.getCustomerId() != customerId, "Der Kunde hat bereits ein Auto gebucht");
        }
        return customerId;
    }

    /**
     * Prueft ob das PickUpDate in der Zukunft liegt
     *
     * @param pickUpDate Die zupruefende PickUpDate
     * @return Abholtermin, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Zukunft liegt
     */
    public static Calendar validatePickUpDate(Calendar pickUpDate) {
        return validateDateFutureForbidden(pickUpDate);
    }

    /**
     * Prueft ob das DropOffDate in der Vergangenheit liegt
     *
     * @param dropOffDate Die zupruefende DropOffDate
     * @return Abgabetermin, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     */
    public static Calendar validateDropOffDate(Calendar dropOffDate) {
        return validateDatePastForbidden(dropOffDate);
    }


}
