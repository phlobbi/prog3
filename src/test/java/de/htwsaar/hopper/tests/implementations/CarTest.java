package de.htwsaar.hopper.tests.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Testklasse fuer die Klasse Car.
 * Hier wird der Konstruktor, so wie die Getter und Setter getestet.
 */
public class CarTest {
    Car setterTestCar;
    Calendar cal;
    Calendar futureCal;

    @Before
    public void setUp() {
        cal = Calendar.getInstance();
        futureCal = Calendar.getInstance();
        futureCal.add(Calendar.YEAR, 1);
        cal.add(Calendar.YEAR, -2);
        setterTestCar = new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test
    public void constructorWithCorrectValues(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullType(){
        new Car(null, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullTransmissionType() {
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, null, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullFuelType() {
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, null, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankBrand(){
        new Car(CarTypeEnum.AUTO, "", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankLicensePlate(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankModel(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullBrand(){
        new Car(CarTypeEnum.AUTO, null, cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullCreatedDate(){
        new Car(CarTypeEnum.AUTO, "BMW", null, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullLicensePlate(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, null, "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullModel(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", null, 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithZeroSeats(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 0, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithTooManySeats(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 302, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeBasePrice(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, -0.1, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeCurrentPrice(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, -0.01, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidLicensePlate(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "ABCD123", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidCreatedDate(){
        new Car(CarTypeEnum.AUTO, "BMW", futureCal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeHorsepower(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 150, "SB-AB-12", "M3", -1, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeMileage(){
        new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 150, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, -1);
    }

    @Test
    public void constructorTrimTest(){
        Car testCar = new Car(CarTypeEnum.AUTO, "   BMW   ", cal, 5, 100, 50, "   SB-AB-12   ", "   M3   ", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
        Car expectedCar = new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
        if (testCar.getBrand().equals(expectedCar.getBrand()) &&
                testCar.getLicensePlate().equals(expectedCar.getLicensePlate()) &&
                testCar.getModel().equals(expectedCar.getModel())) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorWithWhitespaceStrings() {
        new Car(CarTypeEnum.AUTO, " ", cal, 5, 100, 50, " ", " ", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
    }

    @Test
    public void setTypeWithValidValue(){
        setterTestCar.setType(CarTypeEnum.BUS);
        assertEquals(CarTypeEnum.BUS, setterTestCar.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTypeWithNullValue(){
        setterTestCar.setType(null);
    }

    @Test
    public void setBrandWithValidValue(){
        setterTestCar.setBrand("Audi");
        assertEquals("Audi", setterTestCar.getBrand());
    }

    @Test
    public void setBrandWithValidValueTrimTest(){
        setterTestCar.setBrand(" Audi ");
        assertEquals("Audi", setterTestCar.getBrand());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBrandWithBlankValue(){
        setterTestCar.setBrand(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBrandWithNullValue(){
        setterTestCar.setBrand(null);
    }

    @Test
    public void setCreationDateWithValidValue(){
        setterTestCar.setCreationDate(cal);
        assertEquals(cal, setterTestCar.getCreationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCreationDateWithInvalidValue(){
        setterTestCar.setCreationDate(futureCal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCreationDateWithNullValue(){
        setterTestCar.setCreationDate(null);
    }

    @Test
    public void setLicensePlateWithValidValue(){
        setterTestCar.setLicensePlate("AB-CD-123");
        assertEquals("AB-CD-123", setterTestCar.getLicensePlate());
    }

    @Test
    public void setLicensePlateWithValidValueTrimTest(){
        setterTestCar.setLicensePlate(" AB-CD-123 ");
        assertEquals("AB-CD-123", setterTestCar.getLicensePlate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithBlankValue(){
        setterTestCar.setLicensePlate(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithNullValue(){
        setterTestCar.setLicensePlate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithInvalidValue(){
        setterTestCar.setLicensePlate("ABCD123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithInvalidValue2(){
        setterTestCar.setLicensePlate("AB-CD-12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithInvalidValue3(){
        setterTestCar.setLicensePlate("ABCD-EF-12");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithInvalidValue4(){
        setterTestCar.setLicensePlate("AB-CD-EF-12");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLicensePlateWithInvalidValue5(){
        setterTestCar.setLicensePlate("AB-CDE-123");
    }

    @Test
    public void setModelWithValidValue(){
        setterTestCar.setModel("A3");
        assertEquals("A3", setterTestCar.getModel());
    }

    @Test
    public void setModelWithValidValueTrimTest(){
        setterTestCar.setModel(" A3 ");
        assertEquals("A3", setterTestCar.getModel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setModelWithBlankValue(){
        setterTestCar.setModel(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setModelWithNullValue(){
        setterTestCar.setModel(null);
    }

    @Test
    public void setSeatsWithValidValue(){
        setterTestCar.setSeats(4);
        assertEquals(4, setterTestCar.getSeats());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSeatsWithZeroValue(){
        setterTestCar.setSeats(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSeatsWithTooManyValue(){
        setterTestCar.setSeats(302);
    }

    @Test
    public void setBasePriceWithValidValue(){
        setterTestCar.setBasePrice(100);
        if (setterTestCar.getBasePrice() == 100) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBasePriceWithZeroValue(){
        setterTestCar.setBasePrice(0);
    }

    @Test
    public void setCurrentPriceWithValidValue(){
        setterTestCar.setCurrentPrice(100);
        if (setterTestCar.getCurrentPrice() == 100) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentPriceWithZeroValue(){
        setterTestCar.setCurrentPrice(0);
    }

    @Test
    public void setHorsepowerWithValidValue() {
        setterTestCar.setHorsepower(123);
        assertEquals(123, setterTestCar.getHorsepower());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHorsepowerWithZeroValue() {
        setterTestCar.setHorsepower(0);
    }

    @Test
    public void setTransmissionWithValidValue() {
        setterTestCar.setTransmissionType(TransmissionTypeEnum.MANUELL);
        assertEquals(TransmissionTypeEnum.MANUELL, setterTestCar.getTransmissionType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTransmissionWithNullValue() {
        setterTestCar.setTransmissionType(null);
    }

    @Test
    public void setFuelTypeWithValidValue() {
        setterTestCar.setFuelType(FuelTypeEnum.DIESEL);
        assertEquals(FuelTypeEnum.DIESEL, setterTestCar.getFuelType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFuelTypeWithNullValue() {
        setterTestCar.setFuelType(null);
    }

    @Test
    public void testToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(cal.getTime());
        String expected;

        if(Locale.getDefault() == Locale.ENGLISH) {
            expected = String.format("Car{carId=0, type=Car, brand='BMW', creationDate=%s, seats=5, basePrice=100.0, currentPrice=50.0, licensePlate='SB-AB-12', model='M3', horsepower=300, transmissionType=Automatic, fuelType=Gasoline, satNav=true, mileage=10000}", date);
        } else {
            expected = String.format("Car{carId=0, type=Auto, brand='BMW', creationDate=%s, seats=5, basePrice=100.0, currentPrice=50.0, licensePlate='SB-AB-12', model='M3', horsepower=300, transmissionType=Automatik, fuelType=Benzin, satNav=true, mileage=10000}", date);
        }

        assertEquals(expected, setterTestCar.toString());
    }

    @Test
    public void testEqualsWithSameObject() {
        assertEquals(setterTestCar, setterTestCar);
    }

    @Test
    public void testEqualsWithSameValues() {
        Car testCar = new Car(CarTypeEnum.AUTO, "BMW", cal, 5, 100, 50, "SB-AB-12", "M3", 300, TransmissionTypeEnum.AUTOMATIK, FuelTypeEnum.BENZIN, true, 10000);
        assertEquals(setterTestCar, testCar);
    }
    @Test
    public void setSatNavWithValidValue() {
        setterTestCar.setSatNav(true);
        assertEquals(true, setterTestCar.getSatNav());
    }

    @Test
    public void setMileageWithValidValue() {
        setterTestCar.setMileage(123);
        assertEquals(123, setterTestCar.getMileage());
    }

    @Test
    public void setMileageWithZeroValue() {
        setterTestCar.setMileage(0);
        assertEquals(0, setterTestCar.getMileage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMileageWithNegativeValue() {
        setterTestCar.setMileage(-1);
    }
}
