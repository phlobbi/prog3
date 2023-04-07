package de.htwsaar.hopper.ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Controller für die Startseite
 */
public final class FirstViewController {

    /**
     * Wechselt bei Aufruf auf die Buchungsverwaltung.
     *
     * @param event mouse click
     */
    @FXML
    private void switchToCarRental(MouseEvent event) throws IOException {
        App.setRoot("fxml/Booking-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Kundenverwaltung.
     *
     * @param event mouse click
     */
    @FXML
    private void switchToCustomerView(MouseEvent event) throws IOException {
        App.setRoot("fxml/Customer-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Hilfesektion.
     *
     * @param event mouse click
     */
    @FXML
    private void switchToHelpView(MouseEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://phlobbi.github.io/prog3/"));
    }

    /**
     * Wechselt bei Aufruf auf die Autoverwaltung.
     *
     * @param event mouse click
     */
    @FXML
    private void switchToCarManagementView(MouseEvent event) throws IOException {
        App.setRoot("fxml/Car-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Einstellungen.
     *
     * @param event mouse click
     */
    @FXML
    private void switchToSettingsView(MouseEvent event) throws IOException {
        App.setRoot("fxml/settings-view.fxml");
    }
}
