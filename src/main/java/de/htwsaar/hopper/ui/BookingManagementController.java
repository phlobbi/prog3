package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public final class BookingManagementController implements Initializable {

    @FXML
    private Button btnBookCar;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnReturnCar;

    @FXML
    private TableView<Booking> tableView;
    @FXML
    private TableColumn<Booking, String> carIdColumn;

    @FXML
    private TableColumn<Booking, Integer> bookingIdColumn;

    @FXML
    private TableColumn<Booking, String> customerIdColumn;


    //private ObservableList<BookingTableInformation>  list ;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("first-view.fxml");

    }

    @FXML
    void switchToScene(ActionEvent event) {

    }

    @FXML
    void switchToSceneCarBooking(ActionEvent event) {

    }

    /**
     * Mit der Methode showBookingList können Sie in der Tabelle ID der Buchung eines Autos,
     * den Vor und Nachnamen der Person,die das Auto reserviert hat
     *  sowie die Marke des reservierten Autos anzeigen.
     */
    void showBookingList(){
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerShowField"));
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carShowField"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(customerIdColumn,bookingIdColumn,carIdColumn);

        ObservableList<Booking> list = FXCollections.observableArrayList();
        list.addAll(BookingRepository.findAll());

        tableView.setItems(list);
    }


    /**
     * @param location Der Ort, an dem relative Pfade für das Root-Objekt aufgelöst werden, oder
     * {@code null}, wenn der Speicherort nicht bekannt ist.
     *  @param resources Die Ressourcen, die zum Lokalisieren des Root-Objekts verwendet werden, oder {@code null}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showBookingList();
    }
}
