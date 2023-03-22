package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public final class CarManagementController implements Initializable {
    private static Car selectedCar;

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
    private TableView<Car> tableView;

    @FXML
    private TableColumn<Car, String> carBrandColumn;

    @FXML
    private TableColumn<Car, String> carIdColumn;

    @FXML
    private TableColumn<Car, String> carTypeColumn;

    @FXML
    void switchToSceneAddCar(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Car-creation-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
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
        btnCreate.setDisable(true);
        btnRead.setDisable(true);
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        btnGoBack.setDisable(true);

        Stage primaryStage = (Stage) btnCreate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow() {
        btnCreate.setDisable(false);
        btnRead.setDisable(false);
        btnRemove.setDisable(false);
        btnUpdate.setDisable(false);
        btnGoBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnCreate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
    }

    /**
     * Wechselt bei Aufruf auf das Fenster Car-read-view.
     *
     * @param event button click
     */
    @FXML
    void switchToSceneReadCar(ActionEvent event) throws IOException {
        setSelectedCar(tableView.getSelectionModel().getSelectedItem());
        App.setRoot("fxml/Car-read-view.fxml");
    }

    @FXML
    void switchToSceneRemoveCar(ActionEvent event) {

    }

    @FXML
    void switchToSceneUpdateCar(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        try {
            selectedCar = tableView.getSelectionModel().getSelectedItem();
            setSelectedCar(selectedCar);
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

    public static Car getSelectedCar() {
        return selectedCar;
    }

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     *
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("fxml/first-view.fxml");
    }

    /**
     * Wird beim Aufruf der View ausgeführt und bereitet die View entsprechend vor.
     *
     * @param url            Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden, oder
     *                       {@code null}, wenn der Speicherort nicht bekannt ist.
     * @param resourceBundle Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden, oder {@code null}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable();
    }

    /**
     * Quasi wie Initialisierung,
     */
    public void reloadTable() {
        tableView.getItems().clear();

        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        ObservableList<Car> observableList = FXCollections.observableArrayList();
        observableList.addAll(CarRepository.findAll());
        tableView.getItems().addAll(observableList);
        tableView.getSelectionModel().selectFirst();
        if (tableView.getSelectionModel().isEmpty()) {
            btnRead.setDisable(true);
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }


    @FXML
    public void deleteCar() {

        setSelectedCar(tableView.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie den Wagen wirklich löschen?");
        alert.setHeaderText("Wagen wirklich löschen?");
        alert.setContentText("Wagen: "+selectedCar.getCarId()+" "+selectedCar.getBrand()+" "+selectedCar.getType());
        alert.showAndWait();
        if(alert.getResult().getText().equals("OK"))
        {
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
     * Setzt die Variable selectedCar auf ein angegebenes Car-Objekt.
     * @param car Zu setzendes Car-Objekt
     */
    public static void setSelectedCar(Car car) {
        selectedCar = car;
    }
}



