package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Car;

import javax.persistence.PrePersist;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stellt sicher, dass kein Car mit unzulaessigen null-Werten in die DB gelangt
 * @author roblin
 */
public class PreventNullPersistForCar {

    /**
     * Wird automatisiert aufgerufen, wann immer eine Car-Entitaet persistiert werden soll.
     * Prueft alle angegeben Attribute mithilfe der Utils.check()-Methode auf null,
     * indem es sie in einen Stream ueberfuehrt und diesen mit allMatch() untersucht.
     * Wird ein null-Wert erkannt, erfolgt eine IllegalArgumentException mit Message und
     * der Persist der Entitaet wird abgebrochen.
     * @param car Das zu pruefende Car-Objekt.
     */
    @PrePersist
    public void testAttributesOnNull(Car car) {
        Utils.check(Stream.of(car.getType(),
            car.getBrand(),
            car.getCreationDate(),
            car.getSeats(),
            car.getBasePrice(),
            car.getCurrentPrice(),
            car.getLicensePlate(),
            car.getModel(),
            car.getHorsepower(),
            car.getTransmissionType(),
            car.getFuelType(),
            car.getSatNav(),
            car.getMileage())
            .allMatch(Objects::nonNull),"Null-Wert erkannt -> unzulaessig");
    }

}

