package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * UI-Klasse, die die CRUD-Funktionen für den Nutzer bereitstellt.
 */
public final class CustomerManagementController implements Initializable {

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
    private TableView<Customer> tableView;

    private static Customer selectedCustomer;

    @FXML
    private TableColumn<Customer, String> customerFirstNameColumn;

    @FXML
    private TableColumn<Customer, String> customerLastNameColumn;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("fxml/first-view.fxml");
    }



    @FXML
    void switchToSceneAddCustomer(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Customer-creation-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();
        reloadTable();
    }

    public void deleteCustomer(){
        setSelectedCustomer(tableView.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Wollen Sie den Kunden wirklich löschen?");
        alert.setHeaderText("Kunde wirklich löschen?");
        alert.setContentText("Kunde: " + selectedCustomer.getCustomerId() + " " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            CustomerRepository.delete(selectedCustomer);
            reloadTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Der Kunde wurde gelöscht.");
            alert2.show();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Der Kunde wurde nicht gelöscht.");
            alert2.show();
            alert.close();
        }
    }

    public void reloadTable(){
        tableView.getItems().clear();

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        observableList.addAll(CustomerRepository.findAll());
        tableView.getItems().addAll(observableList);
        tableView.getSelectionModel().selectFirst();
        if (tableView.getSelectionModel().isEmpty()) {
            btnRead.setDisable(true);
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    void disableWindow(){
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

    void enableWindow(){
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

    @FXML
    void switchToSceneReadCustomer(ActionEvent event) throws IOException {
        setSelectedCustomer(tableView.getSelectionModel().getSelectedItem());
        App.setRoot("fxml/Customer-read-view.fxml");
    }

    @FXML
    void switchToSceneRemoveCustomer(ActionEvent event) {

    }

    @FXML
    void switchToSceneUpdateCustomer(ActionEvent event) {

    }

        public static Customer getSelectedCustomer() {
            return selectedCustomer;
        }

        /**
         * Wird beim Aufruf der View ausgeführt und bereitet die View entsprechend vor.
         * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden, oder
         * {@code null}, wenn der Speicherort nicht bekannt ist.
         * @param resourceBundle Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden, oder {@code null}
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            ObservableList<Customer> observableList = FXCollections.observableArrayList();
            observableList.addAll(CustomerRepository.findAll());
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
         * @param customer Zu setzendes Car-Objekt
         */
        public static void setSelectedCustomer(Customer customer) {
            selectedCustomer = customer;
        }

}
