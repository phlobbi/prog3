package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.ui.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class BookingManagementController implements Initializable {

    @FXML
    private Button btnBookCar;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnReadBooking;

    @FXML
    private Button btnResetSearch;

    @FXML
    private Button btnReturnCar;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<Booking> tableView;

    @FXML
    private TableColumn<Booking, Integer> bookingIDColumn;

    @FXML
    private TableColumn<Booking, String> customerColumn;

    @FXML
    private TableColumn<Booking, String> carColumn;

    @FXML
    private TableColumn<Booking, String> pickUpDateColumn;

    @FXML
    private TableColumn<Booking, String> dropOffDateColumn;

    @FXML
    private TableColumn<Booking, String> realDropOffDateColumn;

    @FXML
    private TableColumn<Booking, String> checklistColumn;

    @FXML
    private CheckMenuItem filterCar;

    @FXML
    private CheckMenuItem filterCustomer;

    @FXML
    private CheckMenuItem filterDropOffDate;

    @FXML
    private CheckMenuItem filterPickUpDate;

    @FXML
    private MenuButton menuButtonFilter;

    @FXML
    private MenuButton menuButtonShowBookings;

    @FXML
    private MenuItem menuItemShowActive;

    @FXML
    private MenuItem menuItemShowAll;

    @FXML
    private MenuItem menuItemShowDone;

    @FXML
    private MenuItem menuItemUncheck;

    @FXML
    private BorderPane root;

    @FXML
    private TextField textFieldSearch;

    private static Booking selectedBooking;

    public static Booking getSelectedBooking() {
        return selectedBooking;
    }

    public static void setSelectedBooking(Booking seletedBooking) {
        BookingManagementController.selectedBooking = seletedBooking;
    }

    @FXML
    void resetSearch(ActionEvent event) {

    }

    @FXML
    void searchBookings(ActionEvent event) {

    }

    @FXML
    void searchBookingsViaEnter(KeyEvent event) {

    }

    @FXML
    void showActiveBookings(ActionEvent event) {

    }

    @FXML
    void showAllBookings(ActionEvent event) {
        tableView.getItems().clear();

        ObservableList<Booking> list = FXCollections.observableArrayList();
        list.addAll(BookingRepository.findAll());
        tableView.setItems(list);
        if (list.isEmpty()) {
            btnReturnCar.setDisable(true);
        } else {
            tableView.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void showDoneBookings(ActionEvent event) {

    }

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     *
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("fxml/first-view.fxml");
    }

    @FXML
    void switchToSceneNewBooking(ActionEvent event) {
        Stage stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Booking-creation-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();
        showAllBookings(new ActionEvent());
    }

    @FXML
    void switchToSceneReadBooking(ActionEvent event) {

    }

    /**
     * Wechsel zum Fenster ReturnCar.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void switchToSceneReturnCar(ActionEvent event) throws IOException {
        setSelectedBooking(tableView.getSelectionModel().getSelectedItem());
        Parent root = FXMLLoader.load(App.class.getResource("fxml/Booking-car-return-view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        //Die Actionevent von anderen Fenster sind blockiert.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void uncheckFilters(ActionEvent event) {

    }

    void disableWindow() {
        btnBookCar.setDisable(true);
        btnReturnCar.setDisable(true);
        btnGoBack.setDisable(true);
        btnReadBooking.setDisable(true);
        btnSearch.setDisable(true);
        btnResetSearch.setDisable(true);
        menuButtonFilter.setDisable(true);
        menuButtonShowBookings.setDisable(true);
        textFieldSearch.setDisable(true);

        Stage primaryStage = (Stage) btnBookCar.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            e.consume();
        });
    }

    void enableWindow() {
        btnBookCar.setDisable(false);
        btnReturnCar.setDisable(false);
        btnGoBack.setDisable(false);
        btnReadBooking.setDisable(false);
        btnSearch.setDisable(false);
        btnResetSearch.setDisable(false);
        menuButtonFilter.setDisable(false);
        menuButtonShowBookings.setDisable(false);
        textFieldSearch.setDisable(false);

        // Roten Kreuz Button wieder aktivieren
        Stage primaryStage = (Stage) btnBookCar.getScene().getWindow();
        primaryStage.onCloseRequestProperty().set(e -> {
            primaryStage.close();
        });
    }

    private void configureTableView(){
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerShowField"));
        carColumn.setCellValueFactory(new PropertyValueFactory<>("carShowField"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        pickUpDateColumn.setCellValueFactory(new PropertyValueFactory<>("pickUpDateShowField"));
        dropOffDateColumn.setCellValueFactory(new PropertyValueFactory<>("dropOffDateShowField"));
        realDropOffDateColumn.setCellValueFactory(new PropertyValueFactory<>("realDropOffDateShowField"));
        checklistColumn.setCellValueFactory(new PropertyValueFactory<>("checklist"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(bookingIDColumn, customerColumn, carColumn, pickUpDateColumn, dropOffDateColumn, realDropOffDateColumn, checklistColumn);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableView();
        showAllBookings(new ActionEvent());
    }
}
