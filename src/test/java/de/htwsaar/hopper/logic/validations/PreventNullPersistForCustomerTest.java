package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
import org.junit.*;
import java.util.Calendar;

public class PreventNullPersistForCustomerTest {
    private static PreventNullPersistForCustomer preventNullPersist;
    private static Calendar calendar;
    private Customer customer;

    @BeforeClass
    public static void setUpClass() {
        preventNullPersist = new PreventNullPersistForCustomer();

        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
    }

    @Before
    public void setUpCustomer() {
        customer = new Customer();
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithAllValuesNullThrowsException(){
        preventNullPersist.testAttributesOnNull(customer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithFirstnameSetNotNullThrowsException(){
        customer.setFirstName("Max");
        preventNullPersist.testAttributesOnNull(customer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithLastnameSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithEmailSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithStreetSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithHouseNumberSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithZipCodeSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithCitySetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        customer.setCity("Musterstadt");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithPhoneNumberSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        customer.setCity("Musterstadt");
        customer.setPhoneNumber("0987654321");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithIBANSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        customer.setCity("Musterstadt");
        customer.setPhoneNumber("0987654321");
        customer.setIBAN("DE89370400440532013000");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test(expected = IllegalArgumentException.class)
    public void persistWithDriverLicenseNumberSetNotNullThrowsException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        customer.setCity("Musterstadt");
        customer.setPhoneNumber("0987654321");
        customer.setIBAN("DE89370400440532013000");
        customer.setDriverLicenseNumber("B072RRE2I55");
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test
    public void persistWithDriverLicenseExpirationDateSetNotNullThrowsNoException(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        customer.setCity("Musterstadt");
        customer.setPhoneNumber("0987654321");
        customer.setIBAN("DE89370400440532013000");
        customer.setDriverLicenseNumber("B072RRE2I55");
        customer.setDriverLicenseExpirationDate(calendar);
        preventNullPersist.testAttributesOnNull(customer);
    }
    @Test
    public void persistWithAllValuesCorrect(){
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmail("max@mustermann.de");
        customer.setStreet("Musterstraße");
        customer.setHouseNumber("10");
        customer.setZipCode("54321");
        customer.setCity("Musterstadt");
        customer.setPhoneNumber("0987654321");
        customer.setIBAN("DE89370400440532013000");
        customer.setDriverLicenseNumber("B072RRE2I55");
        customer.setDriverLicenseExpirationDate(calendar);
        preventNullPersist.testAttributesOnNull(customer);
        CustomerRepository.persist(customer);
    }
}