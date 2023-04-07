package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.CustomerRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller für die Erstellung eines Kunden
 */
public final class CustomerCreationController {

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
     * Bricht die Erstellung eines Kunden ab
     *
     * @param event ActionEvent
     * @throws IOException IOException
     */
    @FXML
    private void cancelCreation(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Erstellt einen Kunden
     *
     * @param event ActionEvent
     */
    @FXML
    private void createCustomer(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            validateTextField(textFieldFirstName, labelFirstName.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldLastName, labelLastName.getText() + " " + bundle.getString("EMPTY"));
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

            Customer customer = new Customer(firstName, lastName, email, street,
                    streetNumber, zipCode, city, phoneNumber,
                    iban, dlNumber, expirationDateCal);


            CustomerRepository.persist(customer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CUSTOMER_CREATED"));
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_CUSTOMER_CREATION"));
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