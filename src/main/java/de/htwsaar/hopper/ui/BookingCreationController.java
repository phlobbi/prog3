package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.logic.validations.Validation;
import de.htwsaar.hopper.repositories.BookingRepository;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static java.util.Calendar.*;

/**
 * Controller für die Wahl eines Kunden bei der Buchung
 */
public final class BookingCreationController {

    private Car chosenCar;
    private Customer chosenCustomer;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnChooseCar;

    @FXML
    private Button btnChooseCustomer;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker datePickerDropOffDate;

    @FXML
    private DatePicker datePickerPickUpDate;

    @FXML
    private Label labelChosenCar;

    @FXML
    private Label labelChosenCustomer;

    @FXML
    private Label labelDropOffDate;

    @FXML
    private Label labelExplanation;

    @FXML
    private Label labelPickUpDate;

    @FXML
    private TextField textFieldChosenCar;

    @FXML
    private TextField textFieldChosenCustomer;

    @FXML
    private TextField textFieldDropOffHour;

    @FXML
    private TextField textFieldDropOffMinute;

    @FXML
    private TextField textFieldPickUpHour;

    @FXML
    private TextField textFieldPickUpMinute;

    /**
     * bricht die Wahl eines Kunden für die Buchung ab und schließt das Fenster.
     *
     * @param event Event (hier Mausklick)
     */
    @FXML
    private void cancelCreation(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Öffnet ein Fenster, in dem das Auto ausgewählt werden kann, welches für die Buchung ausgeliehen werden soll.
     * Dabei wird die Methode getChosenCar() der Klasse BookingCarChooseController aufgerufen.
     *
     * @param event Event (hier Mausklick)
     */
    @FXML
    void chooseCar(ActionEvent event) {
        Stage stage;
        URL url = getClass().getResource("fxml/Booking-car-choose-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.setTitle(bundle.getString("CAR_CHOOSE"));
            URL iconURL = getClass().getResource("icons/car-icon.png");
            stage.getIcons().add(new Image(iconURL.toString()));
            stage.setMinHeight(720);
            stage.setMinWidth(820);
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();

            BookingCarChooseController controller = fxmlLoader.getController();
            chosenCar = controller.getChosenCar();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();

        if (chosenCar != null) {
            textFieldChosenCar.setText(chosenCar.getBrand() + " " + chosenCar.getModel());
        } else {
            textFieldChosenCar.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("NO_CAR_SELECTED"));
            alert.setHeaderText(bundle.getString("ERROR_SELECTION"));
            alert.showAndWait();
        }
    }

    /**
     * Öffnet ein neues Fenster, in dem der Kunde ausgewählt werden kann, der den Wagen ausleihen möchte.
     * Dabei wird die Methode getChosenCustomer() der Klasse BookingCustomerChooseController aufgerufen.
     *
     * @param event Event (hier Mausklick)
     */
    @FXML
    private void chooseCustomer(ActionEvent event) {
        Stage stage;
        URL url = getClass().getResource("fxml/Booking-customer-choose-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.setTitle(bundle.getString("CUSTOMER_CHOOSE"));
            URL iconURL = getClass().getResource("icons/car-icon.png");
            stage.getIcons().add(new Image(iconURL.toString()));
            stage.setMinHeight(720);
            stage.setMinWidth(820);
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();

            BookingCustomerChooseController controller = fxmlLoader.getController();
            chosenCustomer = controller.getChosenCustomer();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();

        if (chosenCustomer != null) {
            textFieldChosenCustomer.setText(chosenCustomer.getFirstName() + " " + chosenCustomer.getLastName());
        } else {
            textFieldChosenCustomer.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("NO_CUSTOMER_SELECTED"));
            alert.setHeaderText(bundle.getString("ERROR_SELECTION"));
            alert.showAndWait();
        }
    }

    /**
     * Speichert die Buchung in der Datenbank.
     * Nimmt die Daten aus den Textfeldern und DatePickern
     * und übergibt sie an die Methode createBooking() der Klasse BookingRepo.
     *
     * @param event Klick auf den Button "Speichern"
     */
    @FXML
    private void createBooking(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try{
            if (datePickerPickUpDate.getValue() == null) {
                throw new IllegalArgumentException(labelPickUpDate.getText() + " " + bundle.getString("EMPTY"));
            }
            if (datePickerDropOffDate.getValue() == null) {
                throw new IllegalArgumentException(labelDropOffDate.getText() + " " + bundle.getString("EMPTY"));
            }
            if (chosenCar == null) {
                throw new IllegalArgumentException(bundle.getString("NO_CAR_SELECTED"));
            }
            if (chosenCustomer == null) {
                throw new IllegalArgumentException(bundle.getString("NO_CAR_SELECTED"));
            }


            LocalDate pickUpDateLocal = datePickerPickUpDate.getValue();
            // LocalDate vom DatePicker zu Calender-Format
            Date pickUpDate = Date.from(pickUpDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar pickUpDateCal = getInstance();
            pickUpDateCal.setTime(pickUpDate);

            int pickUpHour = Integer.parseInt(textFieldPickUpHour.getText());
            Validation.validateHour(pickUpHour);

            int pickUpMinute = Integer.parseInt(textFieldPickUpMinute.getText());
            Validation.validateMinute(pickUpMinute);

            pickUpDateCal.set(HOUR_OF_DAY, pickUpHour);
            pickUpDateCal.set(MINUTE, pickUpMinute);

            LocalDate dropOffDateLocal = datePickerDropOffDate.getValue();
            // LocalDate vom DatePicker zu Calender-Format
            Date dropOffDate = Date.from(dropOffDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar dropOffDateCal = getInstance();
            dropOffDateCal.setTime(dropOffDate);

            int dropOffHour = Integer.parseInt(textFieldDropOffHour.getText());
            Validation.validateHour(dropOffHour);

            int dropOffMinute = Integer.parseInt(textFieldDropOffMinute.getText());
            Validation.validateMinute(dropOffMinute);

            dropOffDateCal.set(HOUR_OF_DAY, dropOffHour);
            dropOffDateCal.set(MINUTE, dropOffMinute);

            Booking booking = new Booking(chosenCar.getCarId(), chosenCustomer.getCustomerId(), pickUpDateCal, dropOffDateCal);

            BookingRepository.persist(booking);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("BOOKING_CREATED"));
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_BOOKING"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Deaktiviert die Buttons und den X-Button, damit das Hauptfenster nicht geschlossen werden kann
     * und die BookingManagement View nicht mehr bedient werden kann.
     */
    private void disableWindow() {
        btnCancel.setDisable(true);
        btnChooseCustomer.setDisable(true);
        btnChooseCar.setDisable(true);
        btnSave.setDisable(true);


        Stage primaryStage = (Stage) btnSave.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(Event::consume);
    }

    /**
     * Aktiviert die Buttons und den X-Button, damit das Hauptfenster wieder geschlossen werden kann
     * und die BookingManagement View wieder bedient werden kann.
     */
    private void enableWindow() {
        btnCancel.setDisable(false);
        btnChooseCustomer.setDisable(false);
        btnChooseCar.setDisable(false);
        btnSave.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnSave.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> primaryStage.close());
    }


}
