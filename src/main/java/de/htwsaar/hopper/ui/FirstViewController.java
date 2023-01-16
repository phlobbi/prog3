package de.htwsaar.hopper.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class FirstViewController {

    @FXML
    private Button btnCarsManagement;

    @FXML
    private Button btnBookingManagement;

    @FXML
    private Button btnCustomerManagement;

    @FXML
    private Button btnHelp;

    @FXML
    private Label label;

    @FXML
    private BorderPane root;

    @FXML
    private GridPane gridpane;

    /**
     * Wechselt bei Aufruf auf die Buchungsverwaltung.
     * @param event
     */
    @FXML
    void switchToCarRental(MouseEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/Booking-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Kundenverwaltung.
     * @param event
     */
    @FXML
    void switchToCustomerView(MouseEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/Customer-management-view.fxml");
    }

    @FXML
    void switchToHelpView(MouseEvent event)  {
        System.out.println("Help");
    }
    @FXML
    void switchToCarManagementView(MouseEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/Car-view.fxml");
    }
}
