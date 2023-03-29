package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingReadController implements Initializable {

    @FXML
    private Button btnBack;

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

    @FXML
    void close(ActionEvent event) throws IOException {
        App.setRoot("fxml/Booking-management-view.fxml");
    }
}
