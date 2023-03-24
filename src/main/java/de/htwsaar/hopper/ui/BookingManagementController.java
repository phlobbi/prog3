package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public static Booking getSeletedBooking() {
        return seletedBooking;
    }

    public  void setSeletedBooking(Booking seletedBooking) {
        this.seletedBooking = seletedBooking;
    }

    private static Booking seletedBooking ;




    //private ObservableList<BookingTableInformation>  list ;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("fxml/first-view.fxml");

    }

    /**
     * Wechsel zum Fenster ReturnCar.
     * @param event
     * @throws IOException
     */
    @FXML
    void switchToSceneReturnCar(ActionEvent event) throws IOException {
        Booking booking = tableView.getSelectionModel().getSelectedItem();
        setSeletedBooking(booking);
        Parent root = FXMLLoader.load(App.class.getResource("fxml/ReturnCar.fxml")) ;
        Scene scene = new Scene(root) ;
        Stage stage = new Stage() ;
        //Die Actionevent von anderen Fenster sind blockiert.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    void switchToSceneCarBooking(ActionEvent event) {
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Booking-creation-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();
        showBookingList();
    }

    void disableWindow(){
        btnBookCar.setDisable(true);
        btnReturnCar.setDisable(true);
        btnGoBack.setDisable(true);

        Stage primaryStage = (Stage) btnBookCar.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow(){
        btnBookCar.setDisable(false);
        btnReturnCar.setDisable(false);
        btnGoBack.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnBookCar.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
    }

    /**
     * Mit der Methode showBookingList können Sie in der Tabelle ID der Buchung eines Autos,
     * den Vor und Nachnamen der Person,die das Auto reserviert hat
     *  sowie die Marke des reservierten Autos anzeigen.
     */
    void showBookingList(){
        tableView.getItems().clear();

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerShowField"));
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carShowField"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(customerIdColumn,bookingIdColumn,carIdColumn);

        ObservableList<Booking> list = FXCollections.observableArrayList();
        list.addAll(BookingRepository.findAll());
        tableView.setItems(list);
        if(list.isEmpty()){
            btnReturnCar.setDisable(true);
        }else {
            tableView.getSelectionModel().selectFirst();
        }
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
