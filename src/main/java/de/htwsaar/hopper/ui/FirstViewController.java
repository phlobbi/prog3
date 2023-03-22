package de.htwsaar.hopper.ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public final class FirstViewController {
    boolean isEnglish = false;

    /**
     * Wechselt bei Aufruf auf die Buchungsverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCarRental(MouseEvent event) throws IOException {
        App.setRoot("fxml/Booking-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Kundenverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCustomerView(MouseEvent event) throws IOException {
        App.setRoot("fxml/Customer-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Hilfesektion.
     * @param event mouse click
     */
    @FXML
    void switchToHelpView(MouseEvent event)  {
        System.out.println("Help");
    }

    /**
     * Wechselt bei Aufruf auf die Autoverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCarManagementView(MouseEvent event) throws IOException {
        App.setRoot("fxml/Car-view.fxml");
    }

    /**
     * Wechselt bei Aufruf die Sprache.
     * @param event mouse click
     */
    @FXML
    void changeLanguage(MouseEvent event) throws IOException {
        //Locale locale = isEnglish ? new Locale("de") : new Locale("en");

        ResourceBundle bundle = ResourceBundle.getBundle("bundles/resource_bundle", new Locale("en"));

        System.out.println(bundle.getString("Sprache"));



        isEnglish = !isEnglish;
    }
}
