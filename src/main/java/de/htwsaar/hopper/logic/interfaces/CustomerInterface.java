package de.htwsaar.hopper.logic.interfaces;

/**
 * Interface für die Klasse Customer.
 * @author Bennet
 */
public interface CustomerInterface {

    /* GETTER */
    int getCustomerId();
    String getFirstName();
    String getLastName();
    String getMailAddress();
    String getStreet();
    String getHouseNumber();
    int getZipCode();
    String getCity();
    String getPhoneNumber();
    String getIBAN();
    int getDriverLicenseNumber();
    String getDriverLicenseExpirationDate();


    /* SETTER */
    void setCustomerId(int customerId);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setMailAddress(String mailAddress);
    void setStreet(String street);
    void setHouseNumber(String houseNumber);
    void setZipCode(int zipCode);
    void setCity(String city);
    void setPhoneNumber(String phoneNumber);
    void setIBAN(String iban);
    void setDriverLicenseNumber(int driverLicenseNumber);
    void setDriverLicenseExpirationDate(String driverLicenseExpirationDate);

    /* toString */
    String toString();

}
