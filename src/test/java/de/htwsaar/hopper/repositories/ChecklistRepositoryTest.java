package de.htwsaar.hopper.repositories;

import de.htwsaar.hopper.logic.implementations.Checklist;
import de.htwsaar.hopper.TestDBUtils;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.logic.validations.BookingValidation;
import de.htwsaar.hopper.logic.validations.Utils;
import org.hibernate.annotations.Check;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class ChecklistRepositoryTest {

    private static Calendar pickUpDate;

    private static Calendar dropOffDate;

    @BeforeClass
    public static void setUpClass() throws IOException {
        TestDBUtils.prepareTestDB();

        pickUpDate = Calendar.getInstance();
        pickUpDate.add(Calendar.MINUTE, 5);

        dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_YEAR, 1);
    }

    @Before
    public void reloadTestDB() throws IOException {
        TestDBUtils.reloadTestDB();

        Calendar carCreation = Calendar.getInstance();
        carCreation.add(Calendar.YEAR, -1);
        Car car = new Car(CarTypeEnum.AUTO, "BMW", carCreation, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
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

        Checklist checklist = new Checklist(true,true,true,true);
        ChecklistRepository.persist(checklist);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }


    @Test
    public void testAddToBookingTest() {
        Checklist checklist = new Checklist(true,true,true,true);
        ChecklistRepository.persist(checklist);
        checklist = ChecklistRepository.find(1);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);

        checklist.addToBooking(1);
        Checklist result = ChecklistRepository.find(checklist.getChecklistId());
        booking = BookingRepository.find(1);

        assertTrue(booking.getChecklistId() == result.getChecklistId());
    }

    @Test
    public void testCorrectBooleanOfChecklist() {
        Checklist checklist = new Checklist(true,true,true,true);
        ChecklistRepository.persist(checklist);
        checklist = ChecklistRepository.find(1);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);
        checklist.addToBooking(1);

        assertTrue(checklist.isFueledUp()
                        && checklist.isUndamaged()
                        && checklist.isClean()
                        && checklist.isKeyDroppedOff());

    }

    @Test
    public void testChecklistIsNotNull() {
        Checklist checklist = new Checklist(true, true, true, true);
        ChecklistRepository.persist(checklist);
        ChecklistRepository.find(1);

        Booking booking = new Booking(1, 1, pickUpDate, dropOffDate);
        BookingRepository.persist(booking);
        checklist.addToBooking(1);

        booking = BookingRepository.find(1);

        assertNotNull(checklist);

    }

    @Test
    public void testChecklistFind() {
        Checklist result = ChecklistRepository.find(1);
        assert result != null;
    }

    @Test
    public void testChecklistFindWithNegativeId() {
        Checklist result = ChecklistRepository.find(-1);
        assertNull(result);
    }

    @Test
    public void testChecklistFindWithZeroId() {
        Checklist result = ChecklistRepository.find(0);
        assertNull(result);
    }

}
