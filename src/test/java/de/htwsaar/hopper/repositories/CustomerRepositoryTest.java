package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.TestDBUtils;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.Assert.*;

public class CustomerRepositoryTest {

    private static Calendar pickUpDate;
    private static Calendar dropOffDate;

    private static Calendar expirationDate;
    private static Calendar creationDate;
    private static Customer testCustomer;



    @BeforeClass
    public static void setUpClass() throws IOException {
        TestDBUtils.prepareTestDB();

        expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.YEAR, 1);

        creationDate = Calendar.getInstance();
        creationDate.add(Calendar.YEAR, -1);

        pickUpDate = Calendar.getInstance();

        dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);
    }

    @Before
    public void reloadTestDB() throws IOException {
        TestDBUtils.reloadTestDB();

        Car testCar = new Car(CarTypeEnum.AUTO, "BMW", creationDate, 5, 100, 50, "SB-AB-12", "M3");
        CarRepository.persist(testCar);

        testCustomer = new Customer("Max", "Mustermann", "max@muster.de", "Musterstraße", "1", "66111", "Saarbrücken", "068192001", "DE74500105174514856976", "B072RRE2I55", expirationDate);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }

    @Test
    public void testPersist() {
        CustomerRepository.persist(testCustomer);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPersistWithNull() {
        CustomerRepository.persist(null);
    }

    @Test
    public void testPersistToUpdateCustomer() {
        CustomerRepository.persist(testCustomer);

        testCustomer = CustomerRepository.find(1);
        testCustomer.setFirstName("Hans");
        testCustomer.setLastName("Müller");
        testCustomer.setStreet("Musterweg");
        testCustomer.setHouseNumber("2");
        CustomerRepository.persist(testCustomer);

        if (CustomerRepository.find(2) != null) {
            fail("Customer with id 2 should not exist");
        }

        Customer result = CustomerRepository.find(1);
        assertTrue(testCustomer.getFirstName().equals(result.getFirstName())
                && testCustomer.getLastName().equals(result.getLastName())
                && testCustomer.getStreet().equals(result.getStreet())
                && testCustomer.getHouseNumber().equals(result.getHouseNumber()));
    }

    @Test
    public void testFind() {
        CustomerRepository.persist(testCustomer);

        Customer result = CustomerRepository.find(1);

        assertTrue(testCustomer.getFirstName().equals(result.getFirstName())
                && testCustomer.getLastName().equals(result.getLastName())
                && testCustomer.getStreet().equals(result.getStreet())
                && testCustomer.getHouseNumber().equals(result.getHouseNumber()));
    }

    @Test
    public void testFindWithNonExistingId() {
        Customer result = CustomerRepository.find(1);
        assertNull(result);
    }

    @Test
    public void testFindWithNegativeId() {
        Customer result = CustomerRepository.find(-1);
        assertNull(result);
    }

    @Test
    public void testFindWithZeroId() {
        Customer result = CustomerRepository.find(0);
        assertNull(result);
    }

    @Test
    public void testFindAllWithZeroEntries() {
        assertTrue(CustomerRepository.findAll().isEmpty());
    }

    @Test
    public void testFindAllWithOneEntry() {
        CustomerRepository.persist(testCustomer);

        assertEquals(1, CustomerRepository.findAll().size());
    }

    @Test
    public void testFindAllWithTwoEntries() {
        CustomerRepository.persist(testCustomer);

        testCustomer.setFirstName("Hans");
        testCustomer.setLastName("Müller");

        CustomerRepository.persist(testCustomer);

        assertEquals(2, CustomerRepository.findAll().size());
    }

    @Test
    public void testDelete() {
        CustomerRepository.persist(testCustomer);

        testCustomer = CustomerRepository.find(1);

        CustomerRepository.delete(testCustomer);
        assertNull(CustomerRepository.find(1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteWithNull() {
        CustomerRepository.delete(null);
    }

    @Test
    public void testDeleteWithAlreadyDeletedCustomer() {
        CustomerRepository.persist(testCustomer);

        testCustomer = CustomerRepository.find(1);

        CustomerRepository.delete(testCustomer);
        CustomerRepository.delete(testCustomer);

        assertNull(CustomerRepository.find(1));
    }

    @Test
    public void testFindAvailableWithZeroEntries() {
        assertTrue(CustomerRepository.findAvailable().isEmpty());
    }

    @Test
    public void testFindAvailableWithOneEntry() {
        CustomerRepository.persist(testCustomer);

        assertEquals(1, CustomerRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithTwoEntries() {
        CustomerRepository.persist(testCustomer);

        testCustomer.setFirstName("Hans");
        testCustomer.setLastName("Müller");
        CustomerRepository.persist(testCustomer);

        assertEquals(2, CustomerRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneBookedEntryOneAvailable() {
        CustomerRepository.persist(testCustomer);

        testCustomer.setFirstName("Hans");
        testCustomer.setLastName("Müller");
        CustomerRepository.persist(testCustomer);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CustomerRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneUnavailableEntry() {
        CustomerRepository.persist(testCustomer);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(0, CustomerRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneReturnedEntry() {
        CustomerRepository.persist(testCustomer);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CustomerRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneReturnedEntryOneAvailable() {
        CustomerRepository.persist(testCustomer);

        testCustomer.setFirstName("Hans");
        testCustomer.setLastName("Müller");
        CustomerRepository.persist(testCustomer);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(2, CustomerRepository.findAvailable().size());
    }

    @Test
    public void testRemoveOrphan() {
        CustomerRepository.persist(testCustomer);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        testCustomer = CustomerRepository.find(1);

        CustomerRepository.delete(testCustomer);

        assertNull(CustomerRepository.find(1));
        assertNull(BookingRepository.find(1));
        assertNotNull(CarRepository.find(1));
    }

    @Test
    public void testRemoveOrphanWithTwoCustomersOneBooking() {
        CustomerRepository.persist(testCustomer);

        testCustomer.setFirstName("Hans");
        testCustomer.setLastName("Müller");
        CustomerRepository.persist(testCustomer);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        testCustomer = CustomerRepository.find(2);

        CustomerRepository.delete(testCustomer);

        assertNotNull(CustomerRepository.find(1));
        assertNull(CustomerRepository.find(2));
        assertNotNull(BookingRepository.find(1));
        assertNull(BookingRepository.find(2));
        assertNotNull(CarRepository.find(1));
    }
}
