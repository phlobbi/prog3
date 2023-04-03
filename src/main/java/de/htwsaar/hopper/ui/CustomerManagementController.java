package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerManagementController implements Initializable {

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRead;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnResetSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Customer, String> customerCityColumn;

    @FXML
    private TableColumn<Customer, String> customerEmailColumn;

    @FXML
    private TableColumn<Customer, String> customerFirstNameColumn;

    @FXML
    private TableColumn<Customer, String> customerHouseNumberColumn;

    @FXML
    private TableColumn<Customer, String> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> customerLastNameColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    @FXML
    private TableColumn<Customer, String> customerStreetColumn;

    @FXML
    private TableColumn<Customer, String> customerZipCodeColumn;

    @FXML
    private CheckMenuItem filterCity;

    @FXML
    private CheckMenuItem filterEmail;

    @FXML
    private CheckMenuItem filterFirstName;

    @FXML
    private CheckMenuItem filterLastName;

    @FXML
    private MenuButton menuButtonFilter;

    @FXML
    private MenuItem menuItemUncheck;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TextField textFieldSearch;

    private static Customer selectedCustomer;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("fxml/first-view.fxml");
    }


    @FXML
    void searchCustomers(ActionEvent event) {
        try{
            String searchCriteria = textFieldSearch.getText();

            if(searchCriteria.trim().isEmpty()){
                throw new IllegalArgumentException("Kein Suchkriterium eingegeben");
            }

            ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();
            checkMenuItems = getAllSelectedCriteria();

            if (checkMenuItems.isEmpty())
                throw new IllegalArgumentException("Kein Kriterium ausgewählt");


            tableView.getItems().clear();
            for (Customer customer : CustomerRepository.findAll()) {
                for (CheckMenuItem item : checkMenuItems) {
                    boolean allowedToInsert = false;
                    if (item.equals(filterFirstName)) {
                        if (customer.getFirstName().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterLastName)) {
                        if (customer.getLastName().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterEmail)) {
                        if (customer.getEmail().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(filterCity)) {
                        if (customer.getCity().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    }
                    if (!IsCustomerAlreadyInTable(customer)){
                        if (allowedToInsert)
                            tableView.getItems().add(customer);
                    }

                }
            }
            if (tableView.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keine Treffer");
                alert.setHeaderText("Keine Treffer");
                alert.setContentText("Es wurden keine Kunden gefunden, die den Suchkriterien entsprechen");
                alert.showAndWait();
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler bei der Suche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void searchCustomersViaEnter(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            searchCustomers(new ActionEvent());
        }
    }

    /**
     * Setzt die Suche zurück, sodass keine Filterkriterien mehr aktiviert sind,
     * die Tabelle wieder auf original zurückgesetzt wird und das Suchfeld geleert wird
     * @param event Event
     */
    @FXML
    void resetSearch(ActionEvent event) {
        uncheckFilters(new ActionEvent());
        reloadTable();
        textFieldSearch.clear();
    }

    @FXML
    void switchToSceneAddCustomer(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        URL url = getClass().getResource("fxml/Customer-creation-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Neuen Kunden hinzufuegen");
            URL iconURL = getClass().getResource("icons/car-icon.png");
            stage.getIcons().add(new Image(iconURL.toString()));
            stage.setMinHeight(660);
            stage.setMinWidth(675);
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
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        setSelectedCustomer(tableView.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bundle.getString("CUSTOMER_CONFIRM_DELETE"));
        alert.setHeaderText(bundle.getString("CUSTOMER_HEADER_CONFIRM_DELETE"));
        alert.setContentText(bundle.getString("CUSTOMER_CONTENT_TEXT") + " " + selectedCustomer.getCustomerId() + " " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            CustomerRepository.delete(selectedCustomer);
            reloadTable();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CUSTOMER_DELETED"));
            alert2.show();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CUSTOMER_NOT_DELETED"));
            alert2.show();
            alert.close();
        }
    }

    public void reloadTable(){
        tableView.getItems().clear();

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        customerHouseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
        customerCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        customerZipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

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
        btnSearch.setDisable(true);
        btnResetSearch.setDisable(true);
        btnCreate.setDisable(true);
        btnRead.setDisable(true);
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        btnGoBack.setDisable(true);
        menuButtonFilter.setDisable(true);
        textFieldSearch.setDisable(true);
        menuItemUncheck.setDisable(true);

        Stage primaryStage = (Stage) btnCreate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow(){
        btnSearch.setDisable(false);
        btnResetSearch.setDisable(false);
        btnCreate.setDisable(false);
        btnRead.setDisable(false);
        btnRemove.setDisable(false);
        btnUpdate.setDisable(false);
        btnGoBack.setDisable(false);
        menuButtonFilter.setDisable(false);
        textFieldSearch.setDisable(false);
        menuItemUncheck.setDisable(false);

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
    void switchToSceneUpdateCustomer(ActionEvent event) {
        setSelectedCustomer(tableView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        URL url = getClass().getResource("fxml/Customer-edit-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = fxmlLoader.load();
            stage = new Stage();
            stage.setTitle("Kundendaten bearbeiten");
            URL iconURL = getClass().getResource("icons/car-icon.png");
            stage.getIcons().add(new Image(iconURL.toString()));
            stage.setMinHeight(660);
            stage.setMinWidth(675);
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

    @FXML
    void uncheckFilters(ActionEvent event) {
        filterCity.setSelected(false);
        filterEmail.setSelected(false);
        filterFirstName.setSelected(false);
        filterLastName.setSelected(false);
    }

    /**
     * Gibt eine Liste aus mit allen Suchkriterien, die ausgewählt sind
     * @return Liste mit allen ausgewählten Suchkriterien
     */
    private ObservableList<CheckMenuItem> getAllSelectedCriteria(){
        ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();

        if (filterCity.isSelected())
            checkMenuItems.add(filterCity);
        if (filterEmail.isSelected())
            checkMenuItems.add(filterEmail);
        if (filterFirstName.isSelected())
            checkMenuItems.add(filterFirstName);
        if (filterLastName.isSelected())
            checkMenuItems.add(filterLastName);

        return checkMenuItems;
    }

    /**
     * Prüft ob das Auto bereits in der Liste ist
     * @param customer Auto was zu prüfen ist
     * @return true wenn Auto bereits in der Liste ist, sonst false
     */
    private boolean IsCustomerAlreadyInTable(Customer customer){
        return tableView.getItems().contains(customer);
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

        /**
         * Setzt die Variable selectedCar auf ein angegebenes Car-Objekt.
         * @param customer Zu setzendes Car-Objekt
         */

        public static void setSelectedCustomer(Customer customer) {
            selectedCustomer = customer;
        }

        /**
         * Wird beim Aufruf der View ausgeführt und bereitet die View entsprechend vor.
         * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden, oder
         * {@code null}, wenn der Speicherort nicht bekannt ist.
         * @param resourceBundle Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden, oder {@code null}
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            reloadTable();
        }

}
