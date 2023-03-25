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

public class CarRepositoryTest {

    private static Calendar creationDate;

    private static Car testCar;

    @BeforeClass
    public static void setUpClass() throws IOException {
        TestDBUtils.prepareTestDB();

        creationDate = Calendar.getInstance();
        creationDate.add(Calendar.YEAR, -1);
    }

    @Before
    public void reloadTestDB() throws IOException {
        TestDBUtils.reloadTestDB();

        testCar = new Car(CarTypeEnum.AUTO, "BMW", creationDate, 5, 100, 50, "SB-AB-12", "M3");

        Calendar driverLicenseExpiration = Calendar.getInstance();
        driverLicenseExpiration.add(Calendar.YEAR, 1);

        Customer customer = new Customer("Max", "Mustermann", "max@muster.de", "Musterstraße", "1", "66111", "Saarbrücken", "068192001", "DE74500105174514856976", "B072RRE2I55", driverLicenseExpiration);
        CustomerRepository.persist(customer);
    }

    @Test
    public void testPersist() {
        CarRepository.persist(testCar);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPersistWithNull() {
        CarRepository.persist(null);
    }

    @Test
    public void testPersistToUpdateCar() {
        CarRepository.persist(testCar);

        testCar = CarRepository.find(1);
        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Car result = CarRepository.find(1);
        assertTrue(testCar.getCarId() == result.getCarId()
            && testCar.getBrand().equals(result.getBrand())
            && testCar.getModel().equals(result.getModel()));
    }

    @Test
    public void testFind() {
        CarRepository.persist(testCar);

        Car result = CarRepository.find(1);

        assertTrue(testCar.getBrand().equals(result.getBrand())
        && testCar.getModel().equals(result.getModel())
        && testCar.getType() == result.getType()
        && testCar.getSeats() == result.getSeats()
        && testCar.getLicensePlate().equals(result.getLicensePlate()));
    }

    @Test
    public void testFindWithNonExistingId() {
        Car result = CarRepository.find(1);
        assertNull(result);
    }

    @Test
    public void testFindWithNegativeId() {
        Car result = CarRepository.find(-1);
        assertNull(result);
    }

    @Test
    public void testFindWithZeroId() {
        Car result = CarRepository.find(0);
        assertNull(result);
    }

    @Test
    public void testFindAllWithZeroEntires() {
        assertTrue(CarRepository.findAll().isEmpty());
    }

    @Test
    public void findAllWithOneEntry() {
        CarRepository.persist(testCar);
        assertEquals(1, CarRepository.findAll().size());
    }

    @Test
    public void findAllWithTwoEntries() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        assertEquals(2, CarRepository.findAll().size());
    }

    @Test
    public void testDelete() {
        CarRepository.persist(testCar);

        testCar = CarRepository.find(1);

        CarRepository.delete(testCar);
        assertNull(CarRepository.find(1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteWithNull() {
        CarRepository.delete(null);
    }

    @Test
    public void testDeleteWithAlreadyDeletedCar() {
        CarRepository.persist(testCar);

        testCar = CarRepository.find(1);

        CarRepository.delete(testCar);
        CarRepository.delete(testCar);

        assertNull(CarRepository.find(1));
    }

    @Test
    public void testFindAvailableWithZeroEntries() {
        assertTrue(CarRepository.findAvailable().isEmpty());
    }

    @Test
    public void testFindAvailableWithOneEntry() {
        CarRepository.persist(testCar);
        assertEquals(1, CarRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithTwoEntries() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        assertEquals(2, CarRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneBookedEntryOneAvailable() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CarRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneUnavailableEntry() {
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(0, CarRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneReturnedEntry() {
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CarRepository.findAvailable().size());
    }

    @Test
    public void testFindAvailableWithOneReturnedEntryOneAvailable() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(2, CarRepository.findAvailable().size());
    }

    @Test
    public void testFindUnavailableWithZeroEntries() {
        assertTrue(CarRepository.findUnavailable().isEmpty());
    }

    @Test
    public void testFindUnavailableWithOneEntry() {
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CarRepository.findUnavailable().size());
    }

    @Test
    public void testFindUnavailableWithTwoEntries() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CarRepository.findUnavailable().size());
    }

    @Test
    public void testFindUnavailableWithOneBookedEntryOneAvailable() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(1, CarRepository.findUnavailable().size());
    }

    @Test
    public void testFindUnavailableWithOneAvailableEntry() {
        CarRepository.persist(testCar);

        assertEquals(0, CarRepository.findUnavailable().size());
    }

    @Test
    public void testFindUnavailableWithOneReturnedEntry() {
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(0, CarRepository.findUnavailable().size());
    }

    @Test
    public void testFindUnavailableWithOneReturnedEntryOneAvailable() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        assertEquals(0, CarRepository.findUnavailable().size());
    }

    @Test
    public void testRemoveOrphan() {
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        testCar = CarRepository.find(1);

        CarRepository.delete(testCar);

        assertNull(CarRepository.find(1));
        assertNull(BookingRepository.find(1));
    }

    @Test
    public void testRemoveOrphanWithTwoCarsOneBooking() {
        CarRepository.persist(testCar);

        testCar.setBrand("Audi");
        testCar.setModel("A4");
        CarRepository.persist(testCar);

        Calendar pickupDate = Calendar.getInstance();

        Calendar dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_MONTH, 1);

        Booking booking = new Booking(1, 1, pickupDate, dropOffDate);
        BookingRepository.persist(booking);

        testCar = CarRepository.find(2);

        CarRepository.delete(testCar);

        assertNotNull(CarRepository.find(1));
        assertNull(CarRepository.find(2));
        assertNotNull(BookingRepository.find(1));
        assertNull(BookingRepository.find(2));
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }
}
