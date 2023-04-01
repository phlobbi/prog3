package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Checklist;
import de.htwsaar.hopper.logic.validations.Validation;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.ChecklistRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;

public class BookingCarReturnController {


    @FXML
    private CheckBox checkBoxKeyDropped;

    @FXML
    private CheckBox checkBoxCarFueledUp;

    @FXML
    private CheckBox checkBoxCarClean;

    @FXML
    private TextField textFieldHour;

    @FXML
    private TextField textFieldMinute;

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
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        Booking booking = BookingManagementController.getSelectedBooking();

        if (booking.getRealDropOffDate() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("CAR_ALREADY_RETURNED"));
            alert.showAndWait();
        } else {
            LocalDate realDropOffDateLocal = datePicker.getValue();
            if (realDropOffDateLocal == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(bundle.getString("NO_DATE_SELECTED"));
                alert.showAndWait();
            } else  if (textFieldHour.getText().isEmpty() || textFieldMinute.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(bundle.getString("NO_TIME_SELECTED"));
                alert.showAndWait();
            } else {
                try {
                    Date realDropOffDateD = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Calendar realDropOffDateCal = new GregorianCalendar();
                    realDropOffDateCal.setTime(realDropOffDateD);

                    int pickUpHour = Integer.parseInt(textFieldHour.getText());
                    Validation.validateHour(pickUpHour);

                    int pickUpMinute = Integer.parseInt(textFieldMinute.getText());
                    Validation.validateMinute(pickUpMinute);

                    realDropOffDateCal.set(HOUR_OF_DAY, pickUpHour);
                    realDropOffDateCal.set(MINUTE, pickUpMinute);
                    realDropOffDateCal.set(Calendar.SECOND, 59);
                    realDropOffDateCal.set(Calendar.MILLISECOND, 999);

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
                    alert.setContentText(bundle.getString("CAR_RETURNED"));
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(bundle.getString("DATE_IN_PAST"));
                alert.showAndWait();
                }
            }
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
