package de.htwsaar.hopper.tests.implementations;

import de.htwsaar.hopper.TestDBUtils;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Checklist;
import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.logic.validations.BookingValidation;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.ChecklistRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import org.junit.*;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BookingTest {

    private Booking booking;
    private Calendar pickUpDate;
    private Calendar dropOffDate;
    private Calendar sameDayDropOffDate;


    @BeforeClass
    public static void setUpClass() throws IOException {
        TestDBUtils.prepareTestDB();
    }

    @Before
    public void setUp() throws IOException {
        TestDBUtils.reloadTestDB();

        Calendar carCreation = Calendar.getInstance();
        carCreation.add(Calendar.YEAR, -1);
        Car car = new Car(CarTypeEnum.AUTO, "BMW", carCreation, 5, 100, 50, "SB-AB-12", "M3");
        CarRepository.persist(car);

        Car car2 = new Car(CarTypeEnum.AUTO, "Audi", carCreation, 5, 200, 50, "SB-AU-12", "A4");
        CarRepository.persist(car2);

        Calendar driverLicenseExpiration = Calendar.getInstance();
        driverLicenseExpiration.add(Calendar.YEAR, 1);
        Customer customer = new Customer("Max", "Mustermann", "max@muster.de", "Musterstraße", "1", "66111", "Saarbrücken", "068192001", "DE74500105174514856976", "B072RRE2I55" ,driverLicenseExpiration);
        CustomerRepository.persist(customer);

        Customer customer2 = new Customer("Martina", "Musterfrau", "martina@muster.de", "Musterweg", "2", "24729", "Saarbrücken", "068192001", "DE74500105174514856976", "B072RRE2I55" ,driverLicenseExpiration);
        CustomerRepository.persist(customer2);

        pickUpDate = Calendar.getInstance();

        dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_YEAR, 1);

        sameDayDropOffDate = Calendar.getInstance();
        sameDayDropOffDate.add(Calendar.MINUTE, 1);

        booking = new Booking(1, 1, pickUpDate, dropOffDate);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }

    @Test
    public void testGetBookingId() {
        BookingRepository.persist(booking);
        int result = BookingRepository.find(1).getBookingId();
        assertEquals(1, result);
    }

    @Test
    public void testGetCustomerShowField() {
        String result = booking.getCustomerShowField();
        assertEquals("Max Mustermann (ID: 1)", result);
    }

    @Test
    public void testGetCarShowField() {
        String result = booking.getCarShowField();
        assertEquals("BMW M3 (ID: 1)", result);
    }

    @Test
    public void testGetCarId() {
        int result = booking.getCarId();
        assertEquals(1, result);
    }

    @Test
    public void testGetCustomerId() {
        int result = booking.getCustomerId();
        assertEquals(1, result);
    }

    @Test
    public void testGetPickUpDate() {
        Calendar result = booking.getPickUpDate();
        assertEquals(pickUpDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetDropOffDate() {
        Calendar result = booking.getDropOffDate();
        assertEquals(dropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetRealDropOffDateWithNullRealDropOffDate() {
        Calendar result = booking.getRealDropOffDate();
        assertNull(result);
    }

    @Test
    public void testGetRealDropOffDateWithNotNullRealDropOffDate() {
        Calendar realDropOffDate = Calendar.getInstance();
        realDropOffDate.add(Calendar.DAY_OF_MONTH, 2);
        booking.setRealDropOffDate(realDropOffDate);
        Calendar result = booking.getRealDropOffDate();
        assertEquals(realDropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testGetChecklistIdWithNullChecklistId() {
        int result = booking.getChecklistId();
        assertEquals(BookingValidation.CHECKLIST_NULL, result);
    }

    @Test
    public void testGetCheckListIdWithNotNullChecklistId() {
        Checklist cl = new Checklist(true, true, true, true);
        ChecklistRepository.persist(cl);
        booking.setChecklistId(1);
        int result = booking.getChecklistId();
        assertEquals(1, result);
    }

    @Test
    public void testSetCarId() {
        booking.setCarId(2);
        int result = booking.getCarId();
        assertEquals(2, result);
    }

    @Test
    public void testSetCarIdWithSameId() {
        booking.setCarId(1);
        int result = booking.getCarId();
        assertEquals(1, result);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetCarIdWithInvalidId() {
        booking.setCarId(3);
    }

    @Test
    public void testSetCustomerId() {
        booking.setCustomerId(2);
        int result = booking.getCustomerId();
        assertEquals(2, result);
    }

    @Test
    public void testSetCustomerIdWithSameId() {
        booking.setCustomerId(1);
        int result = booking.getCustomerId();
        assertEquals(1, result);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetCustomerIdWithInvalidId() {
        booking.setCustomerId(3);
    }

    @Test
    public void testSetPickUpDateWithValidDate() {
        Calendar newPickUpDate = Calendar.getInstance();
        newPickUpDate.add(Calendar.DAY_OF_YEAR, -2);
        booking.setPickUpDate(newPickUpDate);
        Calendar result = booking.getPickUpDate();
        assertEquals(newPickUpDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testSetPickUpDateWithSameDate() {
        booking.setPickUpDate(pickUpDate);
        Calendar result = booking.getPickUpDate();
        assertEquals(pickUpDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetPickUpDateWithInvalidDate() {
        Calendar newPickUpDate = Calendar.getInstance();
        newPickUpDate.add(Calendar.DAY_OF_YEAR, 2);
        booking.setPickUpDate(newPickUpDate);
    }

    @Test
    public void testSetDropOffDateWithValidDate() {
        Calendar newDropOffDate = Calendar.getInstance();
        newDropOffDate.add(Calendar.DAY_OF_YEAR, 2);
        booking.setDropOffDate(newDropOffDate);
        Calendar result = booking.getDropOffDate();
        assertEquals(newDropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testSetDropOffDateWithSameDate() {
        booking.setDropOffDate(dropOffDate);
        Calendar result = booking.getDropOffDate();
        assertEquals(dropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetDropOffDateWithInvalidDate() {
        Calendar newDropOffDate = Calendar.getInstance();
        newDropOffDate.add(Calendar.DAY_OF_YEAR, -2);
        booking.setDropOffDate(newDropOffDate);
        Calendar result = booking.getDropOffDate();
        assertEquals(dropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testSetRealDropOffDateWithValidDate() {
        Calendar newRealDropOffDate = Calendar.getInstance();
        newRealDropOffDate.add(Calendar.DAY_OF_YEAR, 2);
        booking.setRealDropOffDate(newRealDropOffDate);
        Calendar result = booking.getRealDropOffDate();
        assertEquals(newRealDropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testSetRealDropOffDateWithSameDate() {
        Calendar newRealDropOffDate = Calendar.getInstance();
        newRealDropOffDate.add(Calendar.DAY_OF_YEAR, 2);
        booking.setRealDropOffDate(newRealDropOffDate);
        booking.setRealDropOffDate(newRealDropOffDate);
        Calendar result = booking.getRealDropOffDate();
        assertEquals(newRealDropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testSetRealDropOffDateWithSameDateAsDropOffDate() {
        booking.setRealDropOffDate(dropOffDate);
        Calendar result = booking.getRealDropOffDate();
        assertEquals(dropOffDate.get(Calendar.DAY_OF_MONTH), result.get(Calendar.DAY_OF_MONTH));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetRealDropOffDateWithInvalidDate() {
        Calendar newRealDropOffDate = Calendar.getInstance();
        newRealDropOffDate.add(Calendar.DAY_OF_YEAR, -2);
        booking.setRealDropOffDate(newRealDropOffDate);
    }

    @Test
    public void testSetChecklistId() {
        Checklist cl = new Checklist(true, true, true, true);
        ChecklistRepository.persist(cl);
        booking.setChecklistId(1);
        int result = booking.getChecklistId();
        assertEquals(1, result);
    }

    @Test
    public void testSetChecklistIdSetToNull() {
        Checklist cl = new Checklist(true, true, true, true);
        ChecklistRepository.persist(cl);
        booking.setChecklistId(1);
        booking.setChecklistId(BookingValidation.CHECKLIST_NULL);
        int result = booking.getChecklistId();
        assertEquals(BookingValidation.CHECKLIST_NULL, result);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetChecklistIdWithInvalidId() {
        booking.setChecklistId(1);
    }

    @Test
    public void testCalculatePrice() {
        double result = booking.calculatePrice();
        assertEquals(200, result, 0.001);
    }

    @Test
    public void testCalculatePriceWithSameDayDropOff() {
        booking.setDropOffDate(sameDayDropOffDate);
        double result = booking.calculatePrice();
        assertEquals(150, result, 0.001);
    }

    @Test @Ignore
    public void testCalculateFinalPriceWithOneDayDelay() {
        Calendar realDropOffDate = Calendar.getInstance();
        realDropOffDate.add(Calendar.DAY_OF_YEAR, 2);
        booking.setRealDropOffDate(realDropOffDate);
        double result = booking.calculateFinalPrice();
        assertEquals(260, result, 0.001);
    }

    @Test @Ignore
    public void testCalculateFinalPriceWithTwoDaysDelay() {
        Calendar realDropOffDate = Calendar.getInstance();
        realDropOffDate.add(Calendar.DAY_OF_YEAR, 3);
        booking.setRealDropOffDate(realDropOffDate);
        double result = booking.calculateFinalPrice();
        assertEquals(320, result, 0.001);
    }

    @Test
    public void testCalculateFinalPriceWithSameDayDropOff() {
        booking.setRealDropOffDate(dropOffDate);
        double result = booking.calculateFinalPrice();
        assertEquals(200, result, 0.001);
    }
}
