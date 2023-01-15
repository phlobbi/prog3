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

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event
     */
    @FXML
    void switchToFirstView(ActionEvent event)  {
        App.setRoot("first-view.fxml");

    }

    @FXML
    void switchToScene(ActionEvent event) {

    }

    @FXML
    void switchToSceneCarBooking(ActionEvent event) {

    }

}
