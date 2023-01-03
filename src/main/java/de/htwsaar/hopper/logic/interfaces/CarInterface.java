package de.htwsaar.hopper.logic.interfaces;

public interface CarInterface {

    /*
        GETTER
     */

    //  Auto ID
    int getCarId();

    //  Marke
    String getBrand();

    //  Erstellungsdatum
    String getCreationDate();

    //  Sitze
    int getSeats();

    //  Grundpreis
    double getBasePrice();

    //  Tagespreis
    double getCurrentPrice();

    //  Kennzeichen
    String getLicensePlate();

    //  Modell
    String getModel();


    /*
        SETTER
     */

    //  Auto ID
    void setCarId(int carId);

    //  Marke
    void setBrand(String brand);

    //  Erstellungsdatum
    void setCreationDate(String creationDate);

    //  Sitze
    void setSeats(int seats);

    //  Grundpreis
    void setBasePrice(double basePrice);

    //  Tagespreis
    void setCurrentPrice(double currentPrice);

    //  Kennzeichen
    void setLicensePlate(String licensePlate);

    //  Modell
    void setModel(String model);


    /*
        toString
     */

    //toString
    String toString();
}
