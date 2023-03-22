package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BookingCarChooseController {

    private Car chosenCar;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private Label labelChosenCar;

    @FXML
    private TableView<?> tableViewAvailableCars;

    @FXML
    private TextField textFieldChosenCar;

    @FXML
    private TextField textFieldSearch;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChosenCar(ActionEvent event) {

    }

    @FXML
    void searchForCar(ActionEvent event) {

    }

    public Car getChosenCar(){
        return chosenCar;
    }

}
