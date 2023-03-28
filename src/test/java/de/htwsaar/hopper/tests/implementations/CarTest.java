package de.htwsaar.hopper.tests.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        setterTestCar = new Car(CarTypeEnum.AUTO, "BMW", cal,
                                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test
    public void constructorWithCorrectValues(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullType(){
        new Car(null, "BMW", cal,
                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankBrand(){
        new Car(CarTypeEnum.AUTO, " ", cal,
                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankLicensePlate(){
        new Car(CarTypeEnum.AUTO, "BWM", cal,
                4, 100, 100, " ", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankModel(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, "AB-CD-123", " ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullBrand(){
        new Car(CarTypeEnum.AUTO, null, cal,
                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullCreatedDate(){
        new Car(CarTypeEnum.AUTO, "BMW", null,
                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullLicensePlate(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, null, "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullModel(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, "AB-CD-123", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeSeats(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                -4, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeBasePrice(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, -100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithTooManySeats(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                400, 100, 100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeCurrentPrice(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, -100, "AB-CD-123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidLicensePlate(){
        new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, "ABCD123", "M3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidCreatedDate(){
        new Car(CarTypeEnum.AUTO, "BMW", futureCal,
                4, 100, 100, "AB-CD-123", "M3");
    }

    @Test
    public void constructorTrimTest(){
        Car testCar = new Car(CarTypeEnum.AUTO, " BMW ", cal,
                4, 100, 100, " AB-CD-123 ", " M3 ");
        Car expectedCar = new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, "AB-CD-123", "M3");
        if (testCar.getBrand().equals(expectedCar.getBrand()) &&
                testCar.getLicensePlate().equals(expectedCar.getLicensePlate()) &&
                testCar.getModel().equals(expectedCar.getModel())) {
            assertTrue(true);
        } else {
            fail();
        }
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
    public void setSeatsWithNegativeValue(){
        setterTestCar.setSeats(-4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSeatsWithTooManyValue(){
        setterTestCar.setSeats(400);
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
    public void setBasePriceWithNegativeValue(){
        setterTestCar.setBasePrice(-100);
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
    public void setCurrentPriceWithNegativeValue(){
        setterTestCar.setCurrentPrice(-100);
    }

    @Test
    public void testToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(cal.getTime());
        String expected = String.format("Car{carId=0, type=AUTO, brand='BMW', creationDate=%s, seats=4, basePrice=100.0, currentPrice=100.0, licensePlate='AB-CD-123', model='M3'}", date);
        assertEquals(expected, setterTestCar.toString());
    }

    @Test
    public void testEqualsWithSameObject() {
        assertEquals(setterTestCar, setterTestCar);
    }

    @Test
    public void testEqualsWithSameValues() {
        Car testCar = new Car(CarTypeEnum.AUTO, "BMW", cal,
                4, 100, 100, "AB-CD-123", "M3");
        assertEquals(setterTestCar, testCar);
    }
}
