package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    }


    /**
     * @param url Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden
     * @param resourceBundle Diese Methode initialisiert die Informationen über das ausgewählte Auto
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}