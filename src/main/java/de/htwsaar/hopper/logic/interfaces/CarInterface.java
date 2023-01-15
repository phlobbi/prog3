package de.htwsaar.hopper.logic.interfaces;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;

import java.util.Calendar;

/**
 * Interface für die Car-Klasse.
 * @author Daniel G
 */
public interface CarInterface {

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


    /*  SETTER  */
    void setType(CarTypeEnum type);
    void setBrand(String brand);
    void setCreationDate(Calendar creationDate);
    void setSeats(int seats);
    void setBasePrice(double basePrice);
    void setCurrentPrice(double currentPrice);
    void setLicensePlate(String licensePlate);
    void setModel(String model);
    
    /*  toString  */
    String toString();
}
