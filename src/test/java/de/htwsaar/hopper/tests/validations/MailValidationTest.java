package de.htwsaar.hopper.tests.validations;

import de.htwsaar.hopper.logic.validations.Validation;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailValidationTest {

    @Test (expected = IllegalArgumentException.class)
    public void mailCompletelyEmpty(){
        String mail = "";
        Validation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void mailWithLocalhostShouldBeFalse(){
        String mail = "test@localhost";
        Validation.validateEmail(mail);
    }

    @Test
    public void gmailWorking(){
        String mail = "yeah@gmail.com";
        assertEquals(Validation.validateEmail(mail), mail);
    }

    @Test
    public void gmailBeforeTrimmingWorking(){
        String mail = "yeah@gmail.com    ";
        assertEquals(Validation.validateEmail(mail), mail.trim());
    }

    @Test
    public void hotmailWorking(){
        String mail = "yeah@hotmail.com";
        assertEquals(Validation.validateEmail(mail), mail);
    }

    //Tests decken die common Mails ab, die in der Praxis verwendet werden, da Saarland ja nah an Frankreich
    @Test
    public void tldDEWorking(){
        String mail = "thomas@germanmailservice.de";
        assertEquals(Validation.validateEmail(mail), mail);
    }

    @Test
    public void tldFRWorking(){
        String mail = "jean@frenchmailservice.fr";
        assertEquals(Validation.validateEmail(mail), mail);
    }

    @Test
    public void tldCOUKWorking(){
        String mail = "jake@englishmailservice.co.uk";
        assertEquals(Validation.validateEmail(mail), mail);
    }

    @Test
    public void tldCOMWorking(){
        String mail = "charles@otherenglishmailservice.com";
        assertEquals(Validation.validateEmail(mail), mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void tldHolyShitNotWorking(){
        String mail = "yeah@hotmail.holyshit";
        Validation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void mailWithoutATNotWorking(){
        String mail = "yeah.hotmail.de";
        Validation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void mailWithoutTldNotWorking(){
        String mail = "yeah@hotmail";
        Validation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void localhostMailNotWorking(){
        String mail = "yeah@localhost";
        Validation.validateEmail(mail);
    }

    @Test (expected = IllegalArgumentException.class)
    public void localhostAsTldMailNotWorking(){
        String mail = "yeah@inmyhouse.localhost";
        Validation.validateEmail(mail);
    }


}
