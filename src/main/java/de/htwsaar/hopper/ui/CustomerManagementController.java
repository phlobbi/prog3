package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
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
    private TableColumn<Customer, String> customerSurnameColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerIdColumn;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("first-view.fxml");
    }



    @FXML
    void switchToSceneAddCustomer(ActionEvent event) {

    }

    @FXML
    void switchToSceneReadCustomer(ActionEvent event) throws IOException {
        setSelectedCustomer(tableView.getSelectionModel().getSelectedItem());
        App.setRoot("Customer-read-view.fxml");
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
