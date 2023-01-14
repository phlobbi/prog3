package de.htwsaar.hopper.tests.implementations;

import de.htwsaar.hopper.logic.implementations.Customer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;

/**
 * Diese Klasse testet die Customer-Klasse.
 * Es werden zum Beispiel alle Getter und Setter getestet, aber auch der Konstruktor.
 */
public class CustomerTest {
    Customer setterTestCustomer;
    Calendar cal;

    @Before
    public void setUp() {
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 2);
        setterTestCustomer = new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test
    public void constructorWithCorrectValues() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyFirstNameShouldThrowException() {
        new Customer(1, "", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyLastNameShouldThrowException() {
        new Customer(1, "Max", "",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyEmailShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidEmailShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "maxmustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyStreetShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyHouseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidHouseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "A", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyZipCodeShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidZipCodeShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "123",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyCityShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyPhoneNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidPhoneNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "3456730", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyIbanShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidIbanShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013002",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyDriverLicenseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidDriverLicenseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I65", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithInvalidDriverLicenseExpirationDateThrowException() {
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I56", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullFirstNameShouldThrowException() {
        new Customer(1, null, "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullLastNameShouldThrowException() {
        new Customer(1, "Max", null,
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullEmailShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                null, "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullStreetShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", null, "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullHouseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", null, "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullZipCodeShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", null,
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullCityShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                null, "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullPhoneNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", null, "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullIbanShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", null,
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullDriverLicenseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                null, cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullDriverLicenseExpirationDateShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", null);
    }

    @Test
    public void constructorTrimTest() {
        Customer testCustomer = new Customer(1, "   Max   ", "   Mustermann   ",
                "   max@mustermann.de   ", "   Musterstraße   ", "   1   ", "   12345   ",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "   B072RRE2I55   ", cal);

        Customer expectedCustomer = new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);

        if(testCustomer.getFirstName().equals(expectedCustomer.getFirstName()) &&
                testCustomer.getLastName().equals(expectedCustomer.getLastName()) &&
                testCustomer.getEmail().equals(expectedCustomer.getEmail()) &&
                testCustomer.getStreet().equals(expectedCustomer.getStreet()) &&
                testCustomer.getHouseNumber().equals(expectedCustomer.getHouseNumber()) &&
                testCustomer.getZipCode().equals(expectedCustomer.getZipCode()) &&
                testCustomer.getDriverLicenseNumber().equals(expectedCustomer.getDriverLicenseNumber())) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankFirstNameShouldThrowException() {
        new Customer(1, "   ", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankLastNameShouldThrowException() {
        new Customer(1, "Max", "   ",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankEmailShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "   ", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankStreetShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "", "   ", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankHouseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "   ", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankZipCodeShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "   ",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankCityShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "   ", "0123456789", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankPhoneNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "   ", "DE89370400440532013000",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankIbanShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "   ",
                "B072RRE2I55", cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithBlankDriverLicenseNumberShouldThrowException() {
        new Customer(1, "Max", "Mustermann",
                "max@mustermann.de", "Musterstraße", "1", "12345",
                "Musterstadt", "0123456789", "DE89370400440532013000",
                "   ", cal);
    }

    @Test
    public void setFirstNameWithValidName() {
        setterTestCustomer.setFirstName("Martha");
        assertEquals("Martha", setterTestCustomer.getFirstName());
    }

    @Test
    public void setFirstNameWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setFirstName("   Martha   ");
        assertEquals("Martha", setterTestCustomer.getFirstName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFirstNameWithNullShouldThrowException() {
        setterTestCustomer.setFirstName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFirstNameWithEmptyStringShouldThrowException() {
        setterTestCustomer.setFirstName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFirstNameWithBlankStringShouldThrowException() {
        setterTestCustomer.setFirstName("   ");
    }

    @Test
    public void setLastNameWithValidName() {
        setterTestCustomer.setLastName("Musterfrau");
        assertEquals("Musterfrau", setterTestCustomer.getLastName());
    }

    @Test
    public void setLastNameWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setLastName("   Musterfrau   ");
        assertEquals("Musterfrau", setterTestCustomer.getLastName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLastNameWithNullShouldThrowException() {
        setterTestCustomer.setLastName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLastNameWithEmptyStringShouldThrowException() {
        setterTestCustomer.setLastName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLastNameWithBlankStringShouldThrowException() {
        setterTestCustomer.setLastName("   ");
    }

    @Test
    public void setEmailWithValidEmail() {
        setterTestCustomer.setEmail("martha@musterfrau.de");
        assertEquals("martha@musterfrau.de", setterTestCustomer.getEmail());
    }

    @Test
    public void setEmailWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setEmail("martha@musterfrau.de");
        assertEquals("martha@musterfrau.de", setterTestCustomer.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmailWithInvalidEmailShouldThrowException() {
        setterTestCustomer.setEmail("martha@musterfrau");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmailWithNullShouldThrowException() {
        setterTestCustomer.setEmail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmailWithEmptyStringShouldThrowException() {
        setterTestCustomer.setEmail("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmailWithBlankStringShouldThrowException() {
        setterTestCustomer.setEmail("   ");
    }

    @Test
    public void setStreetWithValidStreet() {
        setterTestCustomer.setStreet("Musterallee");
        assertEquals("Musterallee", setterTestCustomer.getStreet());
    }

    @Test
    public void setStreetWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setStreet("   Musterallee   ");
        assertEquals("Musterallee", setterTestCustomer.getStreet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setStreetWithNullShouldThrowException() {
        setterTestCustomer.setStreet(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setStreetWithEmptyStringShouldThrowException() {
        setterTestCustomer.setStreet("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setStreetWithBlankStringShouldThrowException() {
        setterTestCustomer.setStreet("   ");
    }

    @Test
    public void setHouseNumberWithValidHouseNumber() {
        setterTestCustomer.setHouseNumber("2");
        assertEquals("2", setterTestCustomer.getHouseNumber());
    }

    @Test
    public void setHouseNumberWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setHouseNumber("   2   ");
        assertEquals("2", setterTestCustomer.getHouseNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHouseNumberWithNullShouldThrowException() {
        setterTestCustomer.setHouseNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHouseNumberWithEmptyStringShouldThrowException() {
        setterTestCustomer.setHouseNumber("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHouseNumberWithBlankStringShouldThrowException() {
        setterTestCustomer.setHouseNumber("   ");
    }

    @Test
    public void setZipCodeWithValidZipCode() {
        setterTestCustomer.setZipCode("12346");
        assertEquals("12346", setterTestCustomer.getZipCode());
    }

    @Test
    public void setZipCodeWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setZipCode("   12346   ");
        assertEquals("12346", setterTestCustomer.getZipCode());
    }

    @Test
    public void setZipCodeWithValidZipCodeWithLeadingZero() {
        setterTestCustomer.setZipCode("01234");
        assertEquals("01234", setterTestCustomer.getZipCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZipCodeWithInvalidZipCodeShouldThrowException() {
        setterTestCustomer.setZipCode("1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZipCodeWithNullShouldThrowException() {
        setterTestCustomer.setZipCode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZipCodeWithEmptyStringShouldThrowException() {
        setterTestCustomer.setZipCode("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setZipCodeWithBlankStringShouldThrowException() {
        setterTestCustomer.setZipCode("   ");
    }

    @Test
    public void setCityWithValidCity() {
        setterTestCustomer.setCity("Musterdorf");
        assertEquals("Musterdorf", setterTestCustomer.getCity());
    }

    @Test
    public void setCityWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setCity("   Musterdorf   ");
        assertEquals("Musterdorf", setterTestCustomer.getCity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCityWithNullShouldThrowException() {
        setterTestCustomer.setCity(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCityWithEmptyStringShouldThrowException() {
        setterTestCustomer.setCity("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCityWithBlankStringShouldThrowException() {
        setterTestCustomer.setCity("   ");
    }

    @Test
    public void setPhoneNumberWithValidPhoneNumber() {
        setterTestCustomer.setPhoneNumber("0123456788");
        assertEquals("0123456788", setterTestCustomer.getPhoneNumber());
    }

    @Test
    public void setPhoneNumberWithInternationalPhoneNumber() {
        setterTestCustomer.setPhoneNumber("+49123456788");
        assertEquals("+49123456788", setterTestCustomer.getPhoneNumber());
    }

    @Test
    public void setPhoneNumberWithInternationalPhoneNumberAndSeparator() {
        setterTestCustomer.setPhoneNumber("+49123/456788");
        assertEquals("+49123/456788", setterTestCustomer.getPhoneNumber());
    }

    @Test
    public void setPhoneNumberWithSeparator() {
        setterTestCustomer.setPhoneNumber("0123/456788");
        assertEquals("0123/456788", setterTestCustomer.getPhoneNumber());
    }

    @Test
    public void setPhoneNumberWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setPhoneNumber("   0123456788   ");
        assertEquals("0123456788", setterTestCustomer.getPhoneNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPhoneNumberWithInvalidPhoneNumberShouldThrowException() {
        setterTestCustomer.setPhoneNumber("12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPhoneNumberWithNullShouldThrowException() {
        setterTestCustomer.setPhoneNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPhoneNumberWithEmptyStringShouldThrowException() {
        setterTestCustomer.setPhoneNumber("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPhoneNumberWithBlankStringShouldThrowException() {
        setterTestCustomer.setPhoneNumber("   ");
    }

    @Test
    public void setIbanWithValidIban() {
        setterTestCustomer.setIBAN("DE76500105179673428975");
        assertEquals("DE76500105179673428975", setterTestCustomer.getIBAN());
    }

    @Test
    public void setIbanWithSeparatorShouldSetPlainText() {
        setterTestCustomer.setIBAN("DE76 5001 0517 9673 4289 75");
        assertEquals("DE76500105179673428975", setterTestCustomer.getIBAN());
    }

    @Test
    public void setIbanWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setIBAN("   DE76500105179673428975   ");
        assertEquals("DE76500105179673428975", setterTestCustomer.getIBAN());
    }

    @Test
    public void setIbanWithWhitespaceAndSeparatorShouldSetPlainText() {
        setterTestCustomer.setIBAN("   DE76 5001 0517 9673 4289 75   ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIbanWithInvalidIbanShouldThrowException() {
        setterTestCustomer.setIBAN("DE7650010517967342897");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIbanWithNullShouldThrowException() {
        setterTestCustomer.setIBAN(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIbanWithEmptyStringShouldThrowException() {
        setterTestCustomer.setIBAN("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIbanWithBlankStringShouldThrowException() {
        setterTestCustomer.setIBAN("   ");
    }

    @Test
    public void setDriverLicenseWithValidDriverLicense() {
        setterTestCustomer.setDriverLicenseNumber("B072RRE2I55");
        assertEquals("B072RRE2I55", setterTestCustomer.getDriverLicenseNumber());
    }

    @Test
    public void setDriverLicenseWithWhitespaceShouldBeTrimmed() {
        setterTestCustomer.setDriverLicenseNumber("   B072RRE2I55   ");
        assertEquals("B072RRE2I55", setterTestCustomer.getDriverLicenseNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDriverLicenseWithInvalidDriverLicenseShouldThrowException() {
        setterTestCustomer.setDriverLicenseNumber("B072RRE2I66");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDriverLicenseWithNullShouldThrowException() {
        setterTestCustomer.setDriverLicenseNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDriverLicenseWithEmptyStringShouldThrowException() {
        setterTestCustomer.setDriverLicenseNumber("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDriverLicenseWithBlankStringShouldThrowException() {
        setterTestCustomer.setDriverLicenseNumber("   ");
    }

    @Test
    public void setDriverLicenseExpirationDateWithValidDate() {
        setterTestCustomer.setDriverLicenseExpirationDate(cal);
        assertEquals(cal, setterTestCustomer.getDriverLicenseExpirationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDriverLicenseExpirationDateWithNullShouldThrowException() {
        setterTestCustomer.setDriverLicenseExpirationDate(null);
        assertNull(setterTestCustomer.getDriverLicenseExpirationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDriverLicenseExpirationDateWithPastDateShouldThrowException() {
        cal.set(2010, 1, 1);
        setterTestCustomer.setDriverLicenseExpirationDate(cal);
        assertEquals(cal, setterTestCustomer.getDriverLicenseExpirationDate());
    }
}
