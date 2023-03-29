package de.htwsaar.hopper.logic.interfaces;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;

import java.util.Calendar;

/**
 * Interface für die Car-Klasse.
 * @author Daniel G
 */
public interface CarInterface {

    /* UI-METHODEN */
    String getSatNavShowField();

    /*  GETTER  */
    int getCarId();
    CarTypeEnum getType();
    String getBrand();
    Calendar getCreationDate();
    int getSeats();
    double getBasePrice();
    double getCurrentPrice();
    String getLicensePlate();
    String getModel();
    int getHorsepower();
    TransmissionTypeEnum getTransmissionType();
    FuelTypeEnum getFuelType();
    boolean getSatNav();
    int getMileage();


    /*  SETTER  */
    void setType(CarTypeEnum type);
    void setBrand(String brand);
    void setCreationDate(Calendar creationDate);
    void setSeats(int seats);
    void setBasePrice(double basePrice);
    void setCurrentPrice(double currentPrice);
    void setLicensePlate(String licensePlate);
    void setModel(String model);
    void setHorsepower(int horsepower);
    void setTransmissionType(TransmissionTypeEnum transmissionType);
    void setFuelType(FuelTypeEnum fuelType);
    void setSatNav(boolean satNav);
    void setMileage(int mileage);
    
    /*  toString  */
    String toString();
}
