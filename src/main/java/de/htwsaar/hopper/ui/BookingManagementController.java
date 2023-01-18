package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
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
import java.util.List;
import java.util.ResourceBundle;


public final class BookingManagementController implements Initializable {

    @FXML
    private Button btnBookCar;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnReturnCar;

    @FXML
    private TableView<BookingTableInformation> tableView;
    @FXML
    private TableColumn<BookingTableInformation, String> BrandColumn;

    @FXML
    private TableColumn<BookingTableInformation, Integer> bookingIDColumn;



    @FXML
    private TableColumn<BookingTableInformation, String> customerNameColumn;


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

        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        BrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        List<Booking> bookingList = BookingRepository.findAll() ;
        ObservableList<BookingTableInformation> list = FXCollections.observableArrayList();
        bookingList.forEach(x->{
            int customerId = x.getCustomerId() ;
            int carId = x.getCarId() ;
            int bookingId = x.getBookingId() ;
            String customerName = CustomerRepository.find(customerId).getFirstName() +"\t"+ CustomerRepository.find(customerId).getLastName() ;
            String  brand = CarRepository.find(carId).getBrand() ;
            list.add(new BookingTableInformation(bookingId ,customerName ,brand)) ;
        });
        tableView.getItems().addAll( list) ;

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
