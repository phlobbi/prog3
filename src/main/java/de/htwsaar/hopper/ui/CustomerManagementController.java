package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * UI-Klasse, die die CRUD-Funktionen für den Nutzer bereitstellt.
 */
public final class CustomerManagementController {

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRead;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<?> tableView;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event
     */
    @FXML
    void switchToFirstView(ActionEvent event)  {
        App.setRoot("de/htwsaar/hopper/ui/first-view.fxml");

    }

    @FXML
    void switchToSceneAddCustomer(ActionEvent event) {

    }

    @FXML
    void switchToSceneReadCustomer(ActionEvent event) {

    }

    @FXML
    void switchToSceneRemoveCustomer(ActionEvent event) {

    }

    @FXML
    void switchToSceneUpdateCustomer(ActionEvent event) {

    }

}
