package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Checklist;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.ChecklistRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ReturnCarController {


    @FXML
    private CheckBox checkBoxKeyDropped;

    @FXML
    private CheckBox checkBoxCarFueledUp;

    @FXML
    private CheckBox checkBoxCarClean;
    @FXML
    private BorderPane root;

    @FXML
    private CheckBox checkBoxCarDamage;

    @FXML
    private DatePicker datePicker;

    /**
     * gibt ein gebuchtes Auto zurueck.
     *
     * @param event
     */
    @FXML
    void returnCar(ActionEvent event) {
        Booking booking = BookingManagementController.getSelectedBooking();

        if (booking.getRealDropOffDate() != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Dieses Auto wurde schon zurückgegeben");
            alert.showAndWait();
        } else {
            LocalDate realDropOffDateLocal = datePicker.getValue();
            Date realDropOffDateD = Date.from(realDropOffDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar realDropOffDateCal = Calendar.getInstance();
            realDropOffDateCal.setTime(realDropOffDateD);

            booking.setRealDropOffDate(realDropOffDateCal);
            BookingRepository.persist(booking);

            boolean isCarFueledUp = checkBoxCarFueledUp.isSelected();
            boolean isCarUnDamaged = checkBoxCarDamage.isSelected();
            boolean isCarClean = checkBoxCarClean.isSelected();
            boolean isKeyDropped = checkBoxKeyDropped.isSelected();
            //Erstellt eine Checklist.
            Checklist checklist = new Checklist(isCarFueledUp, isCarUnDamaged, isCarClean, isKeyDropped);
            ChecklistRepository.persist(checklist);
            //Sucht die Letzte checkList, die hinzugefügt wurde.
            checklist = ChecklistRepository.findLastChecklist();
            assert checklist != null;
            checklist.addToBooking(booking.getBookingId());
            System.out.println(realDropOffDateD.getTime());
            Stage stage1 = (Stage) root.getScene().getWindow();
            stage1.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Das Auto wurde zurückgegeben");
            alert.showAndWait();
        }
    }

    /**
     * Schließt das Fenster ReturnCar.
     *
     * @param event
     */
    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
    }
}
