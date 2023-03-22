package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BookingCustomerChooseController {

    private Customer chosenCustomer;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Label labelChosenCustomer;

    @FXML
    private TableView<?> tableViewCustomers;

    @FXML
    private TextField textFieldChosenCustomer;

    @FXML
    private TextField textFieldSearch;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChosenCustomer(ActionEvent event) {

    }

    @FXML
    void searchForCustomer(ActionEvent event) {

    }

    public Customer getChosenCustomer(){
        return chosenCustomer;
    }

}
