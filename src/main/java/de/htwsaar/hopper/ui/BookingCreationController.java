package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Customer;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class BookingCreationController {

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
    void cancelCreation(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Öffnet ein Fenster, in dem das Auto ausgewählt werden kann, welches für die Buchung ausgeliehen werden soll.
     * Dabei wird die Methode getChosenCar() der Klasse BookingCarChooseController aufgerufen.
     *
     * @param event
     */
    @FXML
    void chooseCar(ActionEvent event) {
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Booking-car-choose-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.setTitle("Auto auswählen");
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();

            BookingCarChooseController controller = fxmlLoader.getController();
            chosenCar = controller.getChosenCar();

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();

        if(chosenCar != null){
            textFieldChosenCar.setText(chosenCar.getBrand() + " " + chosenCar.getModel());
        } else {
            textFieldChosenCar.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kein Auto ausgewählt");
            alert.setHeaderText("Fehler bei der Auswahl");
            alert.showAndWait();
        }
    }

    /**
     * Öffnet ein neues Fenster, in dem der Kunde ausgewählt werden kann, der den Wagen ausleihen möchte.
     * Dabei wird die Methode getChosenCustomer() der Klasse BookingCustomerChooseController aufgerufen.
     *
     * @param event
     */
    @FXML
    void chooseCustomer(ActionEvent event) {
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Booking-customer-choose-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.setTitle("Kunde auswählen");
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();

            BookingCustomerChooseController controller = fxmlLoader.getController();
            chosenCustomer = controller.getChosenCustomer();

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();

        if(chosenCustomer != null){
            textFieldChosenCustomer.setText(chosenCustomer.getFirstName() + " " + chosenCustomer.getLastName());
        } else {
            textFieldChosenCar.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kein Auto ausgewählt");
            alert.setHeaderText("Fehler bei der Auswahl");
            alert.showAndWait();
        }
    }


    @FXML
    void createBooking(ActionEvent event) {
        try{

            if(chosenCar == null){
                throw new IllegalArgumentException("Kein Auto ausgewählt");
            }
            if(chosenCustomer == null){
                throw new IllegalArgumentException("Kein Kunde ausgewählt");
            }
            if (datePickerPickUpDate.getValue() == null){
                throw new IllegalArgumentException(labelPickUpDate.getText() + " leer");
            }
            if (datePickerDropOffDate.getValue() == null){
                throw new IllegalArgumentException(labelDropOffDate.getText() + " leer");
            }

            LocalDate pickUpDateLocal = datePickerPickUpDate.getValue();
            // LocalDate vom DatePicker zu Calender-Format
            Date pickUpDate = Date.from(pickUpDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar pickUpDateCal = Calendar.getInstance();
            pickUpDateCal.setTime(pickUpDate);

            LocalDate dropOffDateLocal = datePickerDropOffDate.getValue();
            // LocalDate vom DatePicker zu Calender-Format
            Date dropOffDate = Date.from(dropOffDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar dropOffDateCal = Calendar.getInstance();
            dropOffDateCal.setTime(dropOffDate);

            Booking booking = new Booking();
            booking.setCarId(chosenCar.getCarId());
            booking.setCustomerId(chosenCustomer.getCustomerId());
            booking.setPickUpDate(pickUpDateCal);
            booking.setDropOffDate(dropOffDateCal);

            BookingRepository.persist(booking);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Buchung erfolgreich erstellt");
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Erstellen der Buchung");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    void disableWindow(){
        btnCancel.setDisable(true);
        btnChooseCustomer.setDisable(true);
        btnChooseCar.setDisable(true);
        btnSave.setDisable(true);


        Stage primaryStage = (Stage) btnSave.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow(){
        btnCancel.setDisable(false);
        btnChooseCustomer.setDisable(false);
        btnChooseCar.setDisable(false);
        btnSave.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnSave.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
    }


}
