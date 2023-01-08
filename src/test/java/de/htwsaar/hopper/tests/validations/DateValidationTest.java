package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateValidationTest {

    @Test
    public void dateWorking(){
        Date date = new Date(29-10-2005);
        Validation.validateDate(date);
    }

    @Test (expected = IllegalArgumentException.class)
    public void dateFuture() {
        Date date = new Date(29-10-2069);
        Validation.validateDate(date);

    }
    @Test
    public void dateNow() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        Validation.validateDate(date);
    }
}
