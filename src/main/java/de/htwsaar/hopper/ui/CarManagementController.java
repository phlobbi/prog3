package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        App.setRoot("fxml/Car-creation-view.fxml");
    }

    /**
     * Wechselt bei Aufruf auf das Fenster Car-read-view.
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
    void switchToSceneUpdateCar(ActionEvent event) {

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
     * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden, oder
     * {@code null}, wenn der Speicherort nicht bekannt ist.
     * @param resourceBundle Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden, oder {@code null}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    /**
     * Setzt die Variable selectedCar auf ein angegebenes Car-Objekt.
     * @param car Zu setzendes Car-Objekt
     */
    public static void setSelectedCar(Car car) {
        selectedCar = car;
    }
}


