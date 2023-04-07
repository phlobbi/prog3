package de.htwsaar.hopper.ui;


import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller für die Bearbeitung eines Kunden
 */
public class CustomerEditController implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelLastName;

    @FXML
    private Label labelAdressHouseNumber;

    @FXML
    private Label labelZipCode;

    @FXML
    private Label labelCity;

    @FXML
    private Label labelPhoneNumber;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelDLExpirationDate;

    @FXML
    private Label labelDLNumber;

    @FXML
    private Label labelIBAN;

    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;

    @FXML
    private DatePicker datePickDLExpirationDate;

    @FXML
    private TextField textFieldStreet;

    @FXML
    private TextField textFieldStreetNumber;

    @FXML
    private TextField textFieldZipCode;

    @FXML
    private TextField textFieldCity;

    @FXML
    private TextField textFieldIBAN;
    @FXML
    private TextField textFieldPhoneNumber;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldDLNumber;

    /**
     * Bricht die Bearbeitung eines Kunden ab
     *
     * @param event Event
     * @throws IOException IOException
     */
    @FXML
    void cancelCreation(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Initialisiert die View
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourcebundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, java.util.ResourceBundle resourcebundle) {
        loadCustomer();
    }

    /**
     * Lädt den ausgewählten Kunden in die Felder der View
     */
    @FXML
    void loadCustomer() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            Customer customer = CustomerManagementController.getSelectedCustomer();
            textFieldFirstName.setText(customer.getFirstName());
            textFieldLastName.setText(customer.getLastName());
            textFieldEmail.setText(customer.getEmail());
            textFieldStreet.setText(customer.getStreet());
            textFieldStreetNumber.setText(customer.getHouseNumber());
            textFieldZipCode.setText(customer.getZipCode());
            textFieldCity.setText(customer.getCity());
            textFieldPhoneNumber.setText(customer.getPhoneNumber());
            textFieldDLNumber.setText(customer.getDriverLicenseNumber());
            textFieldIBAN.setText(customer.getIBAN());
            datePickDLExpirationDate.setValue(customer.getDriverLicenseExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_CUSTOMER_LOAD"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Validiert die Eingabe und speichert den geänderten Kunden
     *
     * @param event Event
     */
    @FXML
    void updateCustomer(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            validateTextField(textFieldFirstName, labelFirstName.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldLastName, labelLastName.getText() + " " + bundle.getString("EMPTY"));;
            validateTextField(textFieldPhoneNumber, labelPhoneNumber.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldEmail, labelEmail.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldIBAN, labelIBAN.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldStreet, labelAdressHouseNumber.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldStreetNumber, labelAdressHouseNumber.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldZipCode, labelZipCode.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldCity, labelCity.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldDLNumber, labelDLNumber.getText() + " " + bundle.getString("EMPTY"));
            if (datePickDLExpirationDate.getValue() == null) {
                throw new IllegalArgumentException(labelDLExpirationDate.getText() + " " + bundle.getString("EMPTY"));
            }

            String firstName = textFieldFirstName.getText();
            String lastName = textFieldLastName.getText();
            String email = textFieldEmail.getText();
            String street = textFieldStreet.getText();
            String streetNumber = textFieldStreetNumber.getText();
            String zipCode = textFieldZipCode.getText();
            String city = textFieldCity.getText();
            String phoneNumber = textFieldPhoneNumber.getText();
            String dlNumber = textFieldDLNumber.getText();
            String iban = textFieldIBAN.getText();
            LocalDate dlExpirationDateLocal = datePickDLExpirationDate.getValue();

            // LocalDate vom DatePicker zu Calender-Format
            Date expirationDate = Date.from(dlExpirationDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar expirationDateCal = Calendar.getInstance();
            expirationDateCal.setTime(expirationDate);

            Customer customer = CustomerManagementController.getSelectedCustomer();

            Customer temp = new Customer(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getStreet(),
                    customer.getHouseNumber(), customer.getZipCode(), customer.getCity(), customer.getPhoneNumber(),
                    customer.getIBAN(), customer.getDriverLicenseNumber(),
                    customer.getDriverLicenseExpirationDate());

            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setStreet(street);
            customer.setHouseNumber(streetNumber);
            customer.setZipCode(zipCode);
            customer.setCity(city);
            customer.setPhoneNumber(phoneNumber);
            customer.setDriverLicenseNumber(dlNumber);
            customer.setIBAN(iban);
            customer.setDriverLicenseExpirationDate(expirationDateCal);

            if (customer.equals(temp)) {
                throw new IllegalArgumentException(bundle.getString("NO_CHANGES_MADE"));
            } else {
                CustomerRepository.persist(customer);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CUSTOMER_UPDATED"));
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_CUSTOMER_UPDATE"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Überprüft Textfelder auf Gültigkeit
     *
     * @param textField    betreffendes Textfeld
     * @param errorMessage Fehlermeldung
     */
    private void validateTextField(TextField textField, String errorMessage) {
        if (textField.getText() == null || textField.getText().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}