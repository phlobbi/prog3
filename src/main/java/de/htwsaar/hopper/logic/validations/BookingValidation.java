package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.ChecklistRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import de.htwsaar.hopper.repositories.BookingRepository;
import java.util.Calendar;
import java.util.List;

public class BookingValidation extends Validation {
    public static final int CHECKLIST_NULL = -1;


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
            if(booking.getCarId() == carId) {
                Utils.check(booking.getRealDropOffDate() != null, "Das Auto ist bereits vergeben");
            }
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
            if(booking.getCustomerId() == customerId) {
                Utils.check(booking.getRealDropOffDate() != null, "Der Kunde hat bereits ein Auto gebucht");
            }
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
        return validateDatePastForbiddenMinute(pickUpDate, "Das Abholdatum liegt in der Vergangenheit.");

    }

    /**
     * Prüft, ob das DropOffDate in der Vergangenheit liegt
     *
     * @param dropOffDate Das zu prüfende DropOffDate
     * @return dropOffDate, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     */
    public static Calendar validateDropOffDate(Calendar dropOffDate) {
        return validateDatePastForbiddenMinute(dropOffDate, "Das Abgabedatum liegt in der Vergangenheit.");
    }

    /**
     * Prüft, ob das PickUpDate vor dem DropOffDate liegt
     * @param pickUpDate Abholtermin
     * @param dropOffDate Abgabetermin
     * @throws IllegalArgumentException Falls das PickUpDate nach dem DropOffDate liegt
     */
    public static void validatePickUpDateBeforeDropOffDate(Calendar pickUpDate, Calendar dropOffDate) {
        String errorMessage = "Das Abgabedatum liegt vor dem Abholdatum.";

        // Fall: Stunde gleich, Minute pickUp nach Minute dropOff
        if (pickUpDate.get(Calendar.MINUTE) > (dropOffDate.get(Calendar.MINUTE))
                && pickUpDate.get(Calendar.HOUR_OF_DAY) == (dropOffDate.get(Calendar.HOUR_OF_DAY))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Tag gleich, Stunde später als jetzt
        if(pickUpDate.get(Calendar.HOUR_OF_DAY) > (dropOffDate.get(Calendar.HOUR_OF_DAY))
                && pickUpDate.get(Calendar.DAY_OF_YEAR) == (dropOffDate.get(Calendar.DAY_OF_YEAR))){
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Jahr gleich, Tag später als jetzt
        if (pickUpDate.get(Calendar.DAY_OF_YEAR) > (dropOffDate.get(Calendar.DAY_OF_YEAR))
                && pickUpDate.get(Calendar.YEAR) == (dropOffDate.get(Calendar.YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }

        // Fall: Jahr später als jetzt
        if(pickUpDate.get(Calendar.YEAR) > (dropOffDate.get(Calendar.YEAR))) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Prüft, ob eine Checkliste zur angegebenen ID existiert
     * @param checklistId Die zu prüfende ID
     * @return checklistId, falls gültig
     */
    public static int validateChecklistId(int checklistId) {
        if(checklistId != CHECKLIST_NULL) {
            Utils.check(ChecklistRepository.find(checklistId) != null, "Checkliste existiert nicht");
        }
        return checklistId;
    }
}
