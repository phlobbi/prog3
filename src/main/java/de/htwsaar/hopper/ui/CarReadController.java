package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        Car selectedCar = CarManagementController.getSelectedCar();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie den Wagen wirklich löschen?");
        alert.setHeaderText("Wagen wirklich löschen?");
        alert.setContentText("Wagen: " + selectedCar.getCarId() + " " + selectedCar.getBrand() + " " + selectedCar.getType());
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            CarRepository.delete(selectedCar);
            reloadTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Der Wagen wurde gelöscht.");
            alert2.show();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Der Wagen wurde nicht gelöscht.");
            alert2.show();
            alert.close();

        }
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
        reloadTable() ;
    }
    public void reloadTable(){
        Car car = CarManagementController.getSelectedCar();
        labelCarBasePrice.setText(String.valueOf(car.getBasePrice()));
        labelCarBrand.setText(String.valueOf(car.getBrand()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy") ;
        String date = simpleDateFormat.format(car.getCreationDate().getTime()) ;
        labelCarCreationDate.setText((date));
        labelCarCurrentPrise.setText(String.valueOf(car.getCurrentPrice()));
        labelCarSeats.setText(String.valueOf(car.getSeats()));
        labelCarType.setText(car.getType().getLabel());
        labelcarLicensePlate.setText(car.getLicensePlate());
    }
}
