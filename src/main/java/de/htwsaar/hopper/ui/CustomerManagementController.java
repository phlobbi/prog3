package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
/*
Diese Klasse verwaltet die Kunden .
Es erlaubt euch unter anderen Kunden anzulegen , zu aendern ,zu loeschen und zu aendern .
 */
public class CustomerManagementController {

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
    private BorderPane root;

    @FXML
    private TableView<?> tableView;

    @FXML
    void switchToFirstView(ActionEvent event)  {
        App.setRoot("First-view.fxml");

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
