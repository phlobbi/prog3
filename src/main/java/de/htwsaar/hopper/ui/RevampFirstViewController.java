package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class RevampFirstViewController {

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
        App.setRoot("Booking-management-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Autoverwaltung.
     * @param event
     */
    @FXML
    void switchToCarView(ActionEvent event) {
        App.setRoot("Car-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf die Kundenverwaltung.
     * @param event
     */
    @FXML
    void switchToCustomerView(ActionEvent event)  {
        App.setRoot("Customer-management-view.fxml");

    }

    @FXML
    void switchToHelpView(ActionEvent event)  {
        App.setRoot("Help-view.fxml");

    }
}
