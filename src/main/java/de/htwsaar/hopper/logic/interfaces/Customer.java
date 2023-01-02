package de.htwsaar.hopper.logic.interfaces;

public interface Customer {

    /*
        GETTER
     */

    //  Kunden ID
    int getCustomerId();

    //  Vorname
    String getFirstName();

    //  Nachname
    String getLastName();

    //  Email
    String getMailAddress();

    //  Strasse
    String getStreet();

    //  Hausnummer
    String getHouseNumber();

    //  PLZ
    int getZipCode();

    //  Ort
    String getCity();

    //  Telefonnummer
    String getPhoneNumber();

    //  IBAN
    String getIBAN();

    //  Führerschein Nummer
    int getDriverLicenseNumber();

    //  Führerschein Ablaufdatum
    String getDriverLicenseExpirationDate();


    /*
        SETTER
     */

    //  Kunden ID
    void setCustomerId(int customerId);

    //  Vorname
    void setFirstName(String firstName);

    //  Nachname
    void setLastName(String lastName);

    //  Email
    void setMailAddress(String mailAddress);

    //  Strasse
    void setStreet(String street);

    //  Hausnummer
    void setHouseNumber(String houseNumber);

    //  PLZ
    void setZipCode(int zipCode);

    //  Ort
    void setCity(String city);

    //  Telefonnummer
    void setPhoneNumber(String phoneNumber);

    //  IBAN
    void setIBAN(String iban);

    //  Führerschein Nummer
    void setDriverLicenseNumber(int driverLicenseNumber);

    //  Führerschein Ablaufdatum
    void setDriverLicenseExpirationDate(String driverLicenseExpirationDate);

}
