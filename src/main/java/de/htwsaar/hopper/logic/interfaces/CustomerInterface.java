package de.htwsaar.hopper.logic.interfaces;

import java.util.Date;

/**
 * Interface für die Klasse Customer.
 * @author Bennet
 */
public interface CustomerInterface {

    /* GETTER */
    int getCustomerId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getStreet();
    String getHouseNumber();
    int getZipCode();
    String getCity();
    String getPhoneNumber();
    String getIBAN();
    String getDriverLicenseNumber();
    Date getDriverLicenseExpirationDate();


    /* SETTER */
    void setCustomerId(int customerId);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setEmail(String email);
    void setStreet(String street);
    void setHouseNumber(String houseNumber);
    void setZipCode(int zipCode);
    void setCity(String city);
    void setPhoneNumber(String phoneNumber);
    void setIBAN(String iban);
    void setDriverLicenseNumber(String driverLicenseNumber);
    void setDriverLicenseExpirationDate(Date driverLicenseExpirationDate);

    /* toString */
    String toString();

}
