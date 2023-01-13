package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class BookingManagementController {

    @FXML
    private Button btnBookCar;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnReturnCar;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<?> tableView;

    /*
    Mit Dieser Methode geht man zum Fenster First-view zurueck .
     */
    @FXML
    void switchToFirstView(ActionEvent event)  {
        App.setRoot("First-view.fxml");

    }

    @FXML
    void switchToScene(ActionEvent event) {

    }

    @FXML
    void switchToSceneCarBooking(ActionEvent event) {

    }

}
