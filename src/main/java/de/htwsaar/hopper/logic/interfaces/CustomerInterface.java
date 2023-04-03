package de.htwsaar.hopper.logic.interfaces;

import java.util.Calendar;

/**
 * Interface für die Klasse Customer.
 * @author Bennet
 */
@SuppressWarnings("MissingJavadoc")
public interface CustomerInterface extends DBObjectInterface {

    /* GETTER */
    int getCustomerId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getStreet();
    String getHouseNumber();
    String getZipCode();
    String getCity();
    String getPhoneNumber();
    String getIBAN();
    String getDriverLicenseNumber();
    Calendar getDriverLicenseExpirationDate();


    /* SETTER */
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setEmail(String email);
    void setStreet(String street);
    void setHouseNumber(String houseNumber);
    void setZipCode(String zipCode);
    void setCity(String city);
    void setPhoneNumber(String phoneNumber);
    void setIBAN(String iban);
    void setDriverLicenseNumber(String driverLicenseNumber);
    void setDriverLicenseExpirationDate(Calendar driverLicenseExpirationDate);

    /* toString */
    String toString();

}
