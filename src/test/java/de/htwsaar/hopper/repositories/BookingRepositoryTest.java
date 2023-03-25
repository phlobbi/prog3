package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.TestDBUtils;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Checklist;
import de.htwsaar.hopper.logic.implementations.Customer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class BookingRepositoryTest {

    private static Calendar pickUpDate;

    private static Calendar dropOffDate;

    @BeforeClass
    public static void setUpClass() throws IOException {
        TestDBUtils.prepareTestDB();

        pickUpDate = Calendar.getInstance();

        dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_YEAR, 1);
    }

    @Before
    public void reloadTestDB() throws IOException {
        TestDBUtils.reloadTestDB();

        Calendar carCreation = Calendar.getInstance();
        carCreation.add(Calendar.YEAR, -1);
        Car car = new Car(CarTypeEnum.AUTO, "BMW", carCreation, 5, 100, 50, "SB-AB-12", "M3");
        CarRepository.persist(car);

        car.setBrand("Audi");
        car.setLicensePlate("SB-AB-13");
        car.setModel("A3");
        CarRepository.persist(car);

        Calendar driverLicenseExpiration = Calendar.getInstance();
        driverLicenseExpiration.add(Calendar.YEAR, 1);
        Customer customer = new Customer("Max", "Mustermann", "max@muster.de", "Musterstraße", "1", "66111", "Saarbrücken", "068192001", "DE74500105174514856976", "B072RRE2I55", driverLicenseExpiration);
        CustomerRepository.persist(customer);

        customer.setFirstName("Hans");
        customer.setLastName("Müller");
        customer.setStreet("Musterweg");
        customer.setHouseNumber("2");
        customer.setEmail("hans@mueller.de");
        CustomerRepository.persist(customer);
    }

    @Test
    public void testPersist() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPersistWithNull() {
        BookingRepository.persist(null);
    }

    @Test
    public void testPersistToUpdateBooking() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);

        booking.setCarId(2);
        booking.setCustomerId(2);
        BookingRepository.persist(booking);

        Booking result = BookingRepository.find(1);

        if (BookingRepository.find(2) != null) {
            fail("Doppelte Buchung wurde erstellt, obwohl eine Aktualisierung erwartet wurde.");
        }

        assertTrue(booking.getCarId() == result.getCarId()
                && booking.getCustomerId() == result.getCustomerId()
                && booking.getPickUpDate().equals(result.getPickUpDate())
                && booking.getDropOffDate().equals(result.getDropOffDate()));
    }

    @Test
    public void testPersistToUpdateBookingWithRealDropOffDate() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);

        booking.setCarId(2);
        booking.setCustomerId(2);
        booking.setRealDropOffDate(dropOffDate);
        BookingRepository.persist(booking);

        Booking result = BookingRepository.find(1);

        if (BookingRepository.find(2) != null) {
            fail("Doppelte Buchung wurde erstellt, obwohl eine Aktualisierung erwartet wurde.");
        }

        assertTrue(booking.getCarId() == result.getCarId()
                && booking.getCustomerId() == result.getCustomerId()
                && booking.getPickUpDate().equals(result.getPickUpDate())
                && booking.getDropOffDate().equals(result.getDropOffDate())
                && booking.getRealDropOffDate().equals(result.getRealDropOffDate()));
    }

    @Test
    public void testFind() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        Booking result = BookingRepository.find(1);

        assertTrue(booking.getCarId() == result.getCarId()
                && booking.getCustomerId() == result.getCustomerId()
                && booking.getPickUpDate().equals(result.getPickUpDate())
                && booking.getDropOffDate().equals(result.getDropOffDate()));
    }

    @Test
    public void testFindWithNonExistingId() {
        Booking result = BookingRepository.find(1);
        assertNull(result);
    }

    @Test
    public void testFindWithNegativeId() {
        Booking result = BookingRepository.find(-1);
        assertNull(result);
    }

    @Test
    public void testFindWithZeroId() {
        Booking result = BookingRepository.find(0);
        assertNull(result);
    }

    @Test
    public void findAllWithZeroEntries() {
        List<Booking> result = BookingRepository.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllWithOneEntry() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        List<Booking> result = BookingRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void findAllWithTwoEntries() {
        Booking booking1 = new Booking(1, 1, pickUpDate, dropOffDate);
        Booking booking2 = new Booking(2, 2, pickUpDate, dropOffDate);
        BookingRepository.persist(booking1);
        BookingRepository.persist(booking2);

        List<Booking> result = BookingRepository.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testDelete() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);

        BookingRepository.delete(booking);

        Booking result = BookingRepository.find(1);
        assertNull(result);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteWithNull() {
        BookingRepository.delete(null);
    }

    @Test
    public void testDeleteWithAlreadyDeletedBooking() {
        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);

        BookingRepository.delete(booking);
        BookingRepository.delete(booking);

        Booking result = BookingRepository.find(1);
        assertNull(result);
    }

    @Test
    public void testOrphanRemoval() {
        ChecklistRepository.persist(new Checklist(true, true, true, true));

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        booking.setChecklistId(1);
        BookingRepository.persist(booking);

        booking = BookingRepository.find(1);

        BookingRepository.delete(booking);

        Checklist result = ChecklistRepository.find(1);
        assertNull(result);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }
}
