package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Booking;

import javax.persistence.PrePersist;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stellt sicher, dass kein Booking mit unzulaessigen null-Werten in die DB gelangt
 * @author roblin
 */
public class PreventNullPersistForBooking {

    /**
     * Wird automatisiert aufgerufen, wann immer eine Booking-Entitaet persistiert werden soll.
     * Prueft alle angegeben Attribute mithilfe der Utils.check()-Methode auf null,
     * indem es sie in einen Stream ueberfuehrt und diesen mit allMatch() untersucht.
     * Wird ein null-Wert erkannt, erfolgt eine IllegalArgumentException mit Message und
     * der Persist der Entitaet wird abgebrochen.
     * @param booking Das zu pruefende Booking-Objekt.
     */
    @PrePersist
    public void testAttributesOnNull(Booking booking) {
        Utils.check(Stream.of(booking.getCarId(),
            booking.getCustomerId(),
            booking.getPickUpDate(),
            booking.getDropOffDate())
            .allMatch(Objects::nonNull),"Null-Wert erkannt -> unzulaessig");
    }
}
