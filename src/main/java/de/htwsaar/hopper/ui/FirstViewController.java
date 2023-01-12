package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class FirstViewController {

    @FXML
    private AnchorPane borderpane;

    @FXML
    private Button btnCarsManagement;

    @FXML
    private Button btnBookingManagement;

    @FXML
    private Button btnCustomerManagement;

    @FXML
    private Label label;

    @FXML
    private BorderPane root;

    /*
    Die Methode SwitchToCarRental erlaubt euch zum  Fenster CarRental zu wechseln.
     */
    @FXML
    void SwitchToCarRental(ActionEvent event)  {
        App.setRoot("Booking-management-view.fxml");

    }
    /*
    Die Methode switchToCarView erlaubt euch zum Fenster Car-view wechseln.
     */

    @FXML
    void switchToCarView(ActionEvent event) {
      App.setRoot("Car-view.fxml");
    }

    @FXML
    void switchToCustomerView(ActionEvent event)  {
        App.setRoot("Customer-management-view.fxml");

    }

}
