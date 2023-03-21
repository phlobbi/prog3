package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class CarReadController implements Initializable {

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label labelCarBasePrice;

    @FXML
    private Label labelCarBrand;
    @FXML
    private Label labelCarCreationDate;

    @FXML
    private Label labelCarCurrentPrise;

    @FXML
    private Label labelCarSeats;

    @FXML
    private Label labelCarType;
    @FXML
    private Label labelcarLicensePlate;

    @FXML
    void removeCar(ActionEvent event) {

    }

    /**
     * zeigt das gewaehlte Auto beim Aufruf.
     * @param event
     * @throws IOException
     */
    @FXML
    void switchToCarView(ActionEvent event) throws IOException {
        App.setRoot("fxml/Car-view.fxml");

    }

    @FXML
    void updateCar(ActionEvent event) {

    }


    /**
     * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden
     * @param resourceBundle Diese Methode initialisiert die Informationen über das ausgewählte Auto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Car car = CarManagementController.getSelectedCar();
        labelCarBasePrice.setText(String.valueOf(car.getBasePrice()));
        labelCarBrand.setText(String.valueOf(car.getBrand()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy") ;
        String date = simpleDateFormat.format(car.getCreationDate().getTime()) ;
        labelCarCreationDate.setText((date));
        labelCarCurrentPrise.setText(String.valueOf(car.getCurrentPrice()));
        labelCarSeats.setText(String.valueOf(car.getSeats()));
        labelCarType.setText(car.getType().getLabel());
        labelcarLicensePlate.setText(car.getLicensePlate());

    }
}
