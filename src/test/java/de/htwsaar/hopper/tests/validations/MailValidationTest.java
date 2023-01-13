package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.CustomerValidation;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailValidationTest {

    @Test (expected = IllegalArgumentException.class)
    public void mailCompletelyEmpty(){
        String mail = "";
        CustomerValidation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void mailWithLocalhostShouldBeFalse(){
        String mail = "test@localhost";
        CustomerValidation.validateEmail(mail);
    }

    @Test
    public void gmailWorking(){
        String mail = "yeah@gmail.com";
        assertEquals(CustomerValidation.validateEmail(mail), mail);
    }

    @Test
    public void gmailBeforeTrimmingWorking(){
        String mail = "yeah@gmail.com    ";
        assertEquals(CustomerValidation.validateEmail(mail), mail.trim());
    }

    @Test
    public void hotmailWorking(){
        String mail = "yeah@hotmail.com";
        assertEquals(CustomerValidation.validateEmail(mail), mail);
    }

    //Tests decken die common Mails ab, die in der Praxis verwendet werden, da Saarland ja nah an Frankreich
    @Test
    public void tldDEWorking(){
        String mail = "thomas@germanmailservice.de";
        assertEquals(CustomerValidation.validateEmail(mail), mail);
    }

    @Test
    public void tldFRWorking(){
        String mail = "jean@frenchmailservice.fr";
        assertEquals(CustomerValidation.validateEmail(mail), mail);
    }

    @Test
    public void tldCOUKWorking(){
        String mail = "jake@englishmailservice.co.uk";
        assertEquals(CustomerValidation.validateEmail(mail), mail);
    }

    @Test
    public void tldCOMWorking(){
        String mail = "charles@otherenglishmailservice.com";
        assertEquals(CustomerValidation.validateEmail(mail), mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void tldHolyShitNotWorking(){
        String mail = "yeah@hotmail.holyshit";
        CustomerValidation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void mailWithoutATNotWorking(){
        String mail = "yeah.hotmail.de";
        CustomerValidation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void mailWithoutTldNotWorking(){
        String mail = "yeah@hotmail";
        CustomerValidation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void localhostMailNotWorking(){
        String mail = "yeah@localhost";
        CustomerValidation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void localhostAsTldMailNotWorking(){
        String mail = "yeah@inmyhouse.localhost";
        CustomerValidation.validateEmail(mail);
    }


}
