package de.htwsaar.hopper.logic.interfaces;

/**
 * Interface für die Car-Klasse.
 * @author Daniel G
 */
public interface CarInterface {

    /*  GETTER  */
    int getCarId();
    String getBrand();
    String getCreationDate();
    int getSeats();
    double getBasePrice();
    double getCurrentPrice();
    String getLicensePlate();
    String getModel();


    /*  SETTER  */
    void setCarId(int carId);
    void setBrand(String brand);
    void setCreationDate(String creationDate);
    void setSeats(int seats);
    void setBasePrice(double basePrice);
    void setCurrentPrice(double currentPrice);
    void setLicensePlate(String licensePlate);
    void setModel(String model);
    
    /*  toString  */
    String toString();
}
