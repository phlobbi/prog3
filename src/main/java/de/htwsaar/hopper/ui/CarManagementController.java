package de.htwsaar.hopper.ui;


import de.htwsaar.hopper.logic.implementations.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

public final class CarManagementController {
    private static Car selectedCar ;

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

    public static Car getSelectedCar() {
        return selectedCar;
    }

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("first-view.fxml");

    }

}


