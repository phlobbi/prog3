package de.htwsaar.hopper.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class CarManagementController {

    @FXML
    private Button btnRead;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<?> tableView;

    @FXML
    void switchToSceneAddCar(ActionEvent event) {

    }

    @FXML
    void switchToSceneReadCar(ActionEvent event) {

    }

    @FXML
    void switchToSceneRemoveCar(ActionEvent event) {

    }

    @FXML
    void switchToSceneUpdateCar(ActionEvent event) {

    }

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event
     */
    @FXML
    void switchToFirstView(ActionEvent event)  {
        App.setRoot("First-view.fxml");

    }

}


