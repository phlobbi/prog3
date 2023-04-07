package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für die Ansicht einer Buchung
 */
public final class BookingReadController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Label labelCar;

    @FXML
    private Label labelCustomer;

    @FXML
    private Label labelDropOff;

    @FXML
    private Label labelID;

    @FXML
    private Label labelPickUp;

    @FXML
    private Label labelRealDropOff;

    /**
     * Initialisiert die Ansicht einer Buchung
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int bookingID = BookingManagementController.getSelectedBooking().getBookingId();
        labelID.setText(String.valueOf(bookingID));
        labelCustomer.setText(BookingManagementController.getSelectedBooking().getCustomerShowField());
        labelCar.setText(BookingManagementController.getSelectedBooking().getCarShowField());
        labelPickUp.setText(BookingManagementController.getSelectedBooking().getPickUpDateShowField());
        labelDropOff.setText(BookingManagementController.getSelectedBooking().getDropOffDateShowField());
        labelRealDropOff.setText(BookingManagementController.getSelectedBooking().getRealDropOffDateShowField());
    }


    /**
     * Schließt das Fenster
     *
     * @param event ActionEvent
     * @throws IOException IOException
     */
    @FXML
    private void close(ActionEvent event) throws IOException {
        App.setRoot("fxml/Booking-management-view.fxml");
    }

    /**
     * deaktiviert das Fenster
     */
    private void disableWindow() {
        btnDelete.setDisable(true);
        btnBack.setDisable(true);


        Stage primaryStage = (Stage) btnDelete.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(Event::consume);
    }

    /**
     * aktiviert das Fenster
     */
    private void enableWindow() {
        btnDelete.setDisable(false);
        btnBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnDelete.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> primaryStage.close());
    }

    /**
     * Öffnet in der Read View die Autorückgabe der Buchung
     *
     * @param event ActionEvent
     * @throws IOException IOException
     */
    @FXML
    private void switchToSceneReturnCar(ActionEvent event) throws IOException {
        URL url = getClass().getResource("fxml/Booking-car-return-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");

        BookingManagementController.setSelectedBooking(BookingManagementController.getSelectedBooking());
        Parent root = FXMLLoader.load(url, bundle);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        disableWindow();
        //Die Actionevent von anderen Fenster sind blockiert.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        enableWindow();
        App.setRoot("fxml/Booking-management-view.fxml");
    }
}
