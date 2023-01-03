package de.htwsaar.hopper.logic.interfaces;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;

import java.util.Date;

/**
 * Interface für die Car-Klasse.
 * @author Daniel G
 */
public interface CarInterface {

    /*  GETTER  */
    int getCarId();
    CarTypeEnum getType();
    String getBrand();
    Date getCreationDate();
    int getSeats();
    double getBasePrice();
    double getCurrentPrice();
    String getLicensePlate();
    String getModel();


    /*  SETTER  */
    void setCarId(int carId);
    void setType(CarTypeEnum type);
    void setBrand(String brand);
    void setCreationDate(Date creationDate);
    void setSeats(int seats);
    void setBasePrice(double basePrice);
    void setCurrentPrice(double currentPrice);
    void setLicensePlate(String licensePlate);
    void setModel(String model);
    
    /*  toString  */
    String toString();
}
