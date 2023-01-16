package de.htwsaar.hopper.ui;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public final class FirstViewController {

    /**
     * Wechselt bei Aufruf auf die Buchungsverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCarRental(MouseEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/Booking-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Kundenverwaltung.
     * @param event mouse click
     */
    @FXML
    void switchToCustomerView(MouseEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/Customer-management-view.fxml");
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
    void switchToCarManagementView(MouseEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/Car-view.fxml");
    }
}
