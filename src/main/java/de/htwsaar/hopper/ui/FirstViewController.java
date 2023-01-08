package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class FirstViewController {

    @FXML
    private AnchorPane borderpane;

    @FXML
    private Button btnCarRental;

    @FXML
    private Button btnBuchungVerwaltung;

    @FXML
    private Button btnKundeVerwaltung;

    @FXML
    private Label label;

    @FXML
    private BorderPane root;

    @FXML
    void SwitchToBuchungview(ActionEvent event) {

    }

    @FXML
    void switchToCarView(ActionEvent event) throws IOException {
      App.setRoot("car-view.fxml");
    }

    @FXML
    void switchToCustomerView(ActionEvent event) {

    }

}
