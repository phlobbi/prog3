package de.htwsaar.hopper.logic.validations;

import de.htwsaar.hopper.TestDBUtils;
import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;

public class PreventNullPersistForCarTest {
    private static PreventNullPersistForCar preventNullPersist;
    private static Calendar calendar;
    private Car car;

    @BeforeClass
    public static void setUpClass() throws IOException {
        preventNullPersist = new PreventNullPersistForCar();

        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -3);

        TestDBUtils.prepareTestDB();
    }

    @Before
    public void setUpCar() {
        car = new Car();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestDBUtils.loadBackupDB();
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithAllValuesNullThrowsException(){
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCarTypeNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCarBrandNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test (expected = IllegalArgumentException.class)
    public void persistWithCreationDateNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithSeatsNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(0);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithBasePriceNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(0);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithCurrentPriceNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(0);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithLicensePlateNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate(null);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithModelNotNullThrowsException(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithHorsepowerSetShouldThrowException() {
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        car.setHorsepower(100);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithTransmissionTypeSetShouldThrowException() {
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        car.setHorsepower(100);
        car.setTransmissionType(TransmissionTypeEnum.AUTOMATIK);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithFuelTypeSetShouldThrowException() {
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        car.setHorsepower(100);
        car.setTransmissionType(TransmissionTypeEnum.AUTOMATIK);
        car.setFuelType(FuelTypeEnum.DIESEL);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistWithSatNavSetShouldThrowException() {
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        car.setHorsepower(100);
        car.setFuelType(FuelTypeEnum.DIESEL);
        car.setSatNav(true);
        preventNullPersist.testAttributesOnNull(car);
    }

    @Test
    public void persistWithAllValuesCorrect(){
        car.setType(CarTypeEnum.AUTO);
        car.setBrand("Infinity");
        car.setCreationDate(calendar);
        car.setSeats(7);
        car.setBasePrice(500);
        car.setCurrentPrice(450);
        car.setLicensePlate("NT-RL-1791");
        car.setModel("Lumina");
        car.setHorsepower(100);
        car.setTransmissionType(TransmissionTypeEnum.AUTOMATIK);
        car.setFuelType(FuelTypeEnum.DIESEL);
        car.setSatNav(true);
        car.setMileage(1000);
        preventNullPersist.testAttributesOnNull(car);
        CarRepository.persist(car);
    }
}