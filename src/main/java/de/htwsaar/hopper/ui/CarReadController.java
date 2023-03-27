package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    void removeCar(ActionEvent event) throws IOException {
        Car selectedCar = CarManagementController.getSelectedCar();
        CarManagementController.setSelectedCar(selectedCar);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie den Wagen wirklich löschen?");
        alert.setHeaderText("Wagen wirklich löschen?");
        alert.setContentText("Wagen: " + selectedCar.getCarId() + " " + selectedCar.getBrand() + " " + selectedCar.getType());
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            CarRepository.delete(selectedCar);
            reloadTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Der Wagen wurde gelöscht.");
            alert2.show();
            App.setRoot("fxml/Car-view.fxml");
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
        Stage stage;
        try {
            Car selectedCar = CarManagementController.getSelectedCar();
            CarManagementController.setSelectedCar(selectedCar);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Car-edit-view.fxml"));
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();
        reloadTable();
    }

    void disableWindow() {
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        btnGoBack.setDisable(true);

        Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow() {
        btnRemove.setDisable(false);
        btnUpdate.setDisable(false);
        btnGoBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
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