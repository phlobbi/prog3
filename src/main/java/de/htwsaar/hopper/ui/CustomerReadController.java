package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;


public final class CustomerReadController implements Initializable {

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnRemove;
    @FXML
    private Label labelSurname;
    @FXML
    private Label labelName;
    @FXML
    private Label labelAdress;
    @FXML
    private Label labelEMail;
    @FXML
    private Label labelTelephoneNumber;
    @FXML
    private Label labelDriverLicenseNumber;
    @FXML
    private Label labelDriverLicenseExpirationDate;
    @FXML
    private Label labelIBAN;
    @FXML
    private Button btnGoBack;


    /**
     * zeigt den gewahlten Kunden beim Aufruf.
     * @param event
     * @throws IOException
     */
    @FXML
    void switchToCustomerView(ActionEvent event) throws IOException {
        App.setRoot("fxml/Customer-management-view.fxml");
    }

    @FXML
    void updateCustomer(ActionEvent event) {
        Stage stage;
        try {
            Customer selectedCustomer = CustomerManagementController.getSelectedCustomer();
            CustomerManagementController.setSelectedCustomer(selectedCustomer);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Customer-edit-view.fxml"));
            Parent root1 = fxmlLoader.load();
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

    @FXML
    void deleteCustomer(ActionEvent event) throws IOException {
        Customer selectedCustomer = CustomerManagementController.getSelectedCustomer();
        CustomerManagementController.setSelectedCustomer(selectedCustomer);
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
        App.setRoot("fxml/Customer-management-view.fxml");
    }


    /**
     * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden
     * @param resourceBundle Diese Methode initialisiert die Informationen über das ausgewählte Auto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable();
    }

    private void reloadTable() {
        Customer customer = CustomerManagementController.getSelectedCustomer();
        labelSurname.setText(String.valueOf(customer.getFirstName()));
        labelName.setText(String.valueOf(customer.getLastName()));
        labelAdress.setText(String.valueOf(customer.getStreet() + " " + customer.getHouseNumber() +
                ", " + customer.getZipCode() + " " + customer.getCity()));
        labelEMail.setText(String.valueOf(customer.getEmail()));
        labelTelephoneNumber.setText(String.valueOf(customer.getPhoneNumber()));
        labelDriverLicenseNumber.setText(String.valueOf(customer.getDriverLicenseNumber()));
        Calendar calendar = customer.getDriverLicenseExpirationDate();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(date);
        labelDriverLicenseExpirationDate.setText(formattedDate);
        labelIBAN.setText(String.valueOf(customer.getIBAN()));
    }

    void disableWindow(){
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        btnGoBack.setDisable(true);

        Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow(){
        btnRemove.setDisable(false);
        btnUpdate.setDisable(false);
        btnGoBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnUpdate.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
    }
}