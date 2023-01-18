package de.htwsaar.hopper.ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public final class FirstViewController {

    /**
     * Wechselt bei Aufruf auf die Buchungsverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCarRental(MouseEvent event) throws IOException {
        App.setRoot("Booking-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Kundenverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCustomerView(MouseEvent event) throws IOException {
        App.setRoot("Customer-management-view.fxml");
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
        App.setRoot("Car-view.fxml");
    }
}
