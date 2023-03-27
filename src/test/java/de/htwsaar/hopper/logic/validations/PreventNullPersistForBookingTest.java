package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.TestDBUtils;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.SatNavEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import org.junit.*;

import java.io.IOException;
import java.util.Calendar;


public class PreventNullPersistForBookingTest {
    private static PreventNullPersistForBooking preventNullPersist;
    private static Calendar pickUpDate;
    private static Calendar dropOffDate;
    private static Calendar realDropOffDate;
    private static Booking booking;

    @BeforeClass
    public static void setUpClass() throws IOException {
        preventNullPersist = new PreventNullPersistForBooking();

        realDropOffDate = Calendar.getInstance();
        realDropOffDate.add(Calendar.DAY_OF_YEAR, 2);

        TestDBUtils.prepareTestDB();
    }

    @Before
    public void reloadTestDB() throws IOException {
        TestDBUtils.reloadTestDB();

        pickUpDate = Calendar.getInstance();
        dropOffDate = Calendar.getInstance();
        dropOffDate.add(Calendar.DAY_OF_YEAR, 1);

        Calendar carCreation = Calendar.getInstance();
        carCreation.add(Calendar.YEAR, -1);
        Car car = new Car(CarTypeEnum.AUTO, "BMW", carCreation, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, SatNavEnum.JA, 10000);
        CarRepository.persist(car);

        Calendar driverLicenseExpiration = Calendar.getInstance();
        driverLicenseExpiration.add(Calendar.YEAR, 1);
        Customer customer = new Customer("Max", "Mustermann", "max@muster.de", "Musterstraße", "1", "66111", "Saarbrücken", "068192001", "DE74500105174514856976", "B072RRE2I55" ,driverLicenseExpiration);
        CustomerRepository.persist(customer);
    }

    @Before
    public void setUpBooking() {
        booking = new Booking();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithAllValuesNullThrowsException(){
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCarIDSetNotNullThrowsException(){
        booking.setCarId(1);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCustomerIDSetNotNullThrowsException(){
        booking.setCarId(1);
        booking.setCustomerId(1);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test
    public void persistWithDropOffDateSetNotNullThrowsNoException(){
        booking = new Booking(1, 1, pickUpDate, dropOffDate);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test
    public void persistWithRealDropOffDateSetNotNullThrowsNoException(){
        booking = new Booking(1, 1, pickUpDate, dropOffDate);
        booking.setRealDropOffDate(realDropOffDate);
        preventNullPersist.testAttributesOnNull(booking);
    }

    @Test
    public void persistWithAllValuesCorrect(){
        booking = new Booking(1, 1, pickUpDate, dropOffDate);
        preventNullPersist.testAttributesOnNull(booking);
        BookingRepository.persist(booking);
    }

}