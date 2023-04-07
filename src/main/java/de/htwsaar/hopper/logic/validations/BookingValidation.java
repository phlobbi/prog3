package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.ChecklistRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;

import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Diese Klasse stellt Methoden zur Validierung von Feldern in der Klasse Booking zur Verfügung.
 */
public final class BookingValidation extends Validation {

    /**
     * Privater Konstruktor, um eine Instanziierung zu verhindern.
     */
    private BookingValidation() {
    }

    /**
     * Diese Konstante wird verwendet, um anzuzeigen, dass die ChecklistId nicht gesetzt ist.
     */
    public static final int CHECKLIST_NULL = -1;
    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");

    /**
     * Prüft, ob die CarId existiert und ob das Auto bereits vergeben ist
     *
     * @param carId Die zu prüfende CarId
     * @return carId, falls gültig
     * @throws IllegalArgumentException Falls die carId nicht existiert
     */
    public static int validateCarId(int carId) {
        Utils.check(CarRepository.find(carId) != null, bundle.getString("CARID_DOESNT_EXIST"));
        List<Booking> bookings = BookingRepository.findAll();
        for (Booking booking : bookings) {
            if (booking.getCarId() == carId) {
                Utils.check(booking.getRealDropOffDate() != null, bundle.getString("CAR_ALREADY_BOOKED"));
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
        Utils.check(CustomerRepository.find(customerId) != null, bundle.getString("CUSTOMERID_DOESNT_EXIST"));
        List<Booking> bookings = BookingRepository.findAll();
        for (Booking booking : bookings) {
            if (booking.getCustomerId() == customerId) {
                Utils.check(booking.getRealDropOffDate() != null, bundle.getString("CUSTOMER_ALREADY_BOOKED"));
            }
        }
        return customerId;
    }

    /**
     * Prüft, ob das PickUpDate in der Vergangenheit liegt
     *
     * @param pickUpDate Das zu prüfende PickUpDate
     * @return pickUpDate, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     */
    public static Calendar validatePickUpDate(Calendar pickUpDate) {
        return validateDatePastForbiddenMinute(pickUpDate, bundle.getString("PICK_UP_DATE_IN_PAST"));

    }

    /**
     * Prüft, ob das DropOffDate in der Vergangenheit liegt
     *
     * @param dropOffDate Das zu prüfende DropOffDate
     * @return dropOffDate, falls gültig
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     */
    public static Calendar validateDropOffDate(Calendar dropOffDate) {
        return validateDatePastForbiddenMinute(dropOffDate, bundle.getString("DROP_OFF_DATE_IN_PAST"));
    }

    /**
     * Prüft, ob das RealDropOffDate gültig ist.
     * Hierzu wird überprüft, ob das RealDropOffDate vor dem PickUpDate liegt.
     * Da das RealDropOffDat auch null sein kann, wird hier null zurückgegeben, falls das RealDropOffDate null ist.
     *
     * @param realDropOffDate Das zu prüfende RealDropOffDate
     * @param pickUpDate      Abholtermin
     * @return realDropOffDate, falls gültig; null, falls realDropOffDate null ist
     * @throws IllegalArgumentException Falls das Datum in der Vergangenheit liegt
     */
    public static Calendar validateRealDropOffDate(Calendar realDropOffDate, Calendar pickUpDate) {
        if (realDropOffDate != null) {
            if (realDropOffDate.before(pickUpDate)) {
                throw new IllegalArgumentException(bundle.getString("REAL_DROP_OFF_DATE_BEFORE_PICK_UP_DATE"));
            } else {
                return realDropOffDate;
            }
        }
        return null;
    }

    /**
     * Prüft, ob das PickUpDate vor dem DropOffDate liegt
     *
     * @param pickUpDate  Abholtermin
     * @param dropOffDate Abgabetermin
     * @throws IllegalArgumentException Falls das PickUpDate nach dem DropOffDate liegt
     */
    public static void validatePickUpDateBeforeDropOffDate(Calendar pickUpDate, Calendar dropOffDate) {
        String errorMessage = bundle.getString("DROP_OFF_DATE_BEFORE_PICK_UP_DATE");

        if (pickUpDate.after(dropOffDate)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Prüft, ob eine Checkliste zur angegebenen ID existiert
     *
     * @param checklistId Die zu prüfende ID
     * @return checklistId, falls gültig
     */
    public static int validateChecklistId(int checklistId) {
        if (checklistId != CHECKLIST_NULL) {
            Utils.check(ChecklistRepository.find(checklistId) != null, bundle.getString("CHECKLIST_DOESNT_EXIST"));
        }
        return checklistId;
    }
}
