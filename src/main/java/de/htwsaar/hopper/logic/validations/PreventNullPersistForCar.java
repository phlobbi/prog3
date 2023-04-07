package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Car;

import javax.persistence.PrePersist;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Stellt sicher, dass kein Car mit unzulässigen null-Werten in die DB gelangt
 *
 * @author roblin
 */
public final class PreventNullPersistForCar {

    /**
     * Wird automatisiert aufgerufen, wann immer eine Car-Entität persistiert werden soll.
     * Prüft alle angegeben Attribute mithilfe der Utils.check()-Methode auf null,
     * indem es sie in einen Stream überführt und diesen mit allMatch() untersucht.
     * Wird ein null-Wert erkannt, erfolgt eine IllegalArgumentException mit Message und
     * der Persist der Entität wird abgebrochen.
     *
     * @param car Das zu prüfende Car-Objekt.
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
                .allMatch(Objects::nonNull), "Null-Wert erkannt -> unzulässig");
    }

}

