package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Booking;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.logic.implementations.Invoice;
import de.htwsaar.hopper.repositories.BookingRepository;
import de.htwsaar.hopper.repositories.CarRepository;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Eine Klasse, die die Logik für die Buchungsverwaltung enthält.
 */
@SuppressWarnings("MissingJavadoc")
public class BookingManagementController implements Initializable {

    @FXML
    public Button btnGenerateInvoice;

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

    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_DONE = 2;
    private static final int STATUS_ALL = 3;

    private static int tableViewStatus;
    private static Booking selectedBooking;

    /**
     * Gibt die ausgewählte Buchung zurück
     *
     * @return selectedBooking
     */
    public static Booking getSelectedBooking() {
        return selectedBooking;
    }

    /**
     * Setzt die ausgewählte Buchung
     *
     * @param selectedBooking die ausgewählte Buchung
     */
    public static void setSelectedBooking(Booking selectedBooking) {
        BookingManagementController.selectedBooking = selectedBooking;
    }

    /**
     * Suche zurücksetzen
     *
     * @param event Event
     */
    @FXML
    void resetSearch(ActionEvent event) {
        uncheckFilters(new ActionEvent());
        textFieldSearch.setText("");
        reloadTable();
    }

    /**
     * Buchung suchen
     *
     * @param event Event
     */
    @FXML
    void searchBookings(ActionEvent event) {
        try {
            String searchCriteria = textFieldSearch.getText();
            if (searchCriteria.trim().isEmpty())
                throw new IllegalArgumentException("Kein Suchkriterium eingegeben");


            ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();
            checkMenuItems = getAllSelectedCriteria();
            if (checkMenuItems.isEmpty())
                throw new IllegalArgumentException("Kein Filter ausgewählt");

            ObservableList<Booking> currentItems = FXCollections.observableArrayList();
            switch (tableViewStatus) {
                case STATUS_ACTIVE:
                    currentItems.addAll(BookingRepository.findUncompleted());
                    break;
                case STATUS_DONE:
                    currentItems.addAll(BookingRepository.findCompleted());
                    break;
                case STATUS_ALL:
                    currentItems.addAll(BookingRepository.findAll());
                    break;
            }

            ObservableList<Booking> itemsAfterSearch = FXCollections.observableArrayList();

            for (Booking booking : currentItems) {
                for (CheckMenuItem item : checkMenuItems) {
                    boolean allowedToInsert = false;
                    if (item.equals(filterCustomer)) {
                        if (booking.getCustomerShowField().toLowerCase().contains(searchCriteria.toLowerCase())) {
                            allowedToInsert = true;
                        }
                    } else if (item.equals(filterCar)) {
                        if (booking.getCarShowField().toLowerCase().contains(searchCriteria.toLowerCase())) {
                            allowedToInsert = true;
                        }
                    } else if (item.equals(filterPickUpDate)) {
                        if (booking.getPickUpDateShowField().contains(searchCriteria)) {
                            allowedToInsert = true;
                        }
                    } else if (item.equals(filterDropOffDate)) {
                        if (booking.getDropOffDateShowField().contains(searchCriteria)) {
                            allowedToInsert = true;
                        }
                    }
                    if (!IsBookingAlreadyInTable(booking)) {
                        if (allowedToInsert)
                            itemsAfterSearch.add(booking);
                    }
                }
            }
            tableView.setItems(itemsAfterSearch);

            if (tableView.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keine Buchungen gefunden");
                alert.setHeaderText("Keine Buchungen gefunden");
                alert.setContentText("Es wurden keine Buchungen gefunden, die den Suchkriterien entsprechen");
                alert.showAndWait();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler bei der Suche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void searchBookingsViaEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            searchBookings(new ActionEvent());
        }
    }


    /**
     * Zeigt alle aktiven Buchungen an
     *
     * @param event Event
     */
    @FXML
    void showActiveBookings(ActionEvent event) {
        tableView.getItems().clear();
        menuButtonShowBookings.setText("Aktive");

        ObservableList<Booking> list = FXCollections.observableArrayList();
        list.addAll(BookingRepository.findUncompleted());
        tableView.setItems(list);
        if (list.isEmpty()) {
            btnReturnCar.setDisable(true);
        } else {
            btnReturnCar.setDisable(false);
            tableView.getSelectionModel().selectFirst();
        }
        tableViewStatus = STATUS_ACTIVE;

    }

    /**
     * Zeigt alle Buchungen an, die bereits zurückgegeben wurden
     *
     * @param event Event
     */
    @FXML
    void showDoneBookings(ActionEvent event) {
        tableView.getItems().clear();
        menuButtonShowBookings.setText("Erledigte");

        ObservableList<Booking> list = FXCollections.observableArrayList();
        list.addAll(BookingRepository.findCompleted());
        tableView.setItems(list);

        btnReturnCar.setDisable(true);
        tableView.getSelectionModel().selectFirst();
        tableViewStatus = STATUS_DONE;

    }


    /**
     * Zeigt alle Buchungen an
     *
     * @param event Event
     */
    @FXML
    void showAllBookings(ActionEvent event) {
        tableView.getItems().clear();
        menuButtonShowBookings.setText("Alle");

        ObservableList<Booking> list = FXCollections.observableArrayList();
        list.addAll(BookingRepository.findAll());
        tableView.setItems(list);
        if (list.isEmpty()) {
            btnReturnCar.setDisable(true);
        } else {
            btnReturnCar.setDisable(false);
            tableView.getSelectionModel().selectFirst();
        }
        tableViewStatus = STATUS_ALL;

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

    /**
     * Wechselt bei Aufruf zu dem Fenster zum Erstellen einer neuen Buchung.
     *
     * @param event button click
     */
    @FXML
    void switchToSceneNewBooking(ActionEvent event) {
        Stage stage;
        URL url = getClass().getResource("fxml/Booking-creation-view.fxml");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url, bundle);
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.setScene(new Scene(root1));
            disableWindow();
            stage.setTitle("Neue Buchung");
            URL iconURL = getClass().getResource("icons/car-icon.png");
            stage.getIcons().add(new Image(iconURL.toString()));
            stage.setMinHeight(480);
            stage.setMinWidth(605);
            stage.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
        enableWindow();
        reloadTable();
    }

    /**
     * Wechselt bei Aufruf zu dem Fenster zum Einsehen einer Buchung.
     *
     * @param event button click
     */
    @FXML
    void switchToSceneReadBooking(ActionEvent event) throws IOException {
        setSelectedBooking(tableView.getSelectionModel().getSelectedItem());
        if (selectedBooking == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Keine Buchung ausgewählt.");
            alert.showAndWait();
            return;
        } else {
            App.setRoot("fxml/Booking-read-view.fxml");
        }

    }

    /**
     * Wechsel zum Fenster ReturnCar.
     *
     * @param event button click
     * @throws IOException Exception
     */
    @FXML
    void switchToSceneReturnCar(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        setSelectedBooking(tableView.getSelectionModel().getSelectedItem());
        URL url = sceneChooser();
        if (selectedBooking.getRealDropOffDate() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Die Buchung wurde bereits zurückgegeben.");
            alert.showAndWait();
            return;
        } else {
            Parent root = FXMLLoader.load(url, bundle);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Auto zurueckgeben");
            URL iconURL = getClass().getResource("icons/car-icon.png");
            stage.getIcons().add(new Image(iconURL.toString()));
            stage.setMinHeight(500);
            stage.setMinWidth(600);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            reloadTable();
        }

    }

    /**
     * Setzt die gesetzten Filter zurück
     *
     * @param event Event des Buttons
     */
    @FXML
    void uncheckFilters(ActionEvent event) {
        filterCustomer.setSelected(false);
        filterCar.setSelected(false);
        filterPickUpDate.setSelected(false);
        filterDropOffDate.setSelected(false);
    }

    /**
     * deaktiviert die Buttons und das Schließen des Fensters
     */
    void disableWindow() {
        btnGenerateInvoice.setDisable(true);
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

    /**
     * aktiviert die Buttons und das Schließen des Fensters
     */
    void enableWindow() {
        btnGenerateInvoice.setDisable(false);
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

    /**
     * generiert Rechnung für die ausgewählte Buchung.
     */
    @FXML
    void generateInvoice() {
        setSelectedBooking(tableView.getSelectionModel().getSelectedItem());
        if (selectedBooking == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Keine Buchung ausgewählt.");
            alert.showAndWait();
        } else if (selectedBooking.getRealDropOffDate() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Das Auto wurde noch nicht abgegeben.");
            alert.showAndWait();
            return;
        }
        Invoice.generate(getSelectedBooking());
    }

    /**
     * Gibt die gewählten Buchungen nach Kriterien zurück
     *
     * @return Alle ausgewählten Filter
     */
    private ObservableList<CheckMenuItem> getAllSelectedCriteria() {
        ObservableList<CheckMenuItem> items = FXCollections.observableArrayList();
        if (filterCustomer.isSelected())
            items.add(filterCustomer);
        if (filterCar.isSelected())
            items.add(filterCar);
        if (filterPickUpDate.isSelected())
            items.add(filterPickUpDate);
        if (filterDropOffDate.isSelected())
            items.add(filterDropOffDate);
        return items;
    }

    /**
     * Gibt Buchung zurück, wenn sie in der Tabelle ist
     *
     * @param booking Buchung die schon in der Tabelle ist
     * @return true, wenn Buchung in Tabelle ist
     */
    private boolean IsBookingAlreadyInTable(Booking booking) {
        return tableView.getItems().contains(booking);
    }

    /**
     * Konfiguriert die Tabelle
     * lädt die Spalten
     */
    private void configureTableView() {
        bookingIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerShowField"));
        carColumn.setCellValueFactory(new PropertyValueFactory<>("carShowField"));
        pickUpDateColumn.setCellValueFactory(new PropertyValueFactory<>("pickUpDateShowField"));
        dropOffDateColumn.setCellValueFactory(new PropertyValueFactory<>("dropOffDateShowField"));
        realDropOffDateColumn.setCellValueFactory(new PropertyValueFactory<>("realDropOffDateShowField"));
        checklistColumn.setCellValueFactory(new PropertyValueFactory<>("checklist"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(bookingIDColumn, customerColumn, carColumn, pickUpDateColumn, dropOffDateColumn, realDropOffDateColumn, checklistColumn);
    }

    /**
     * Lädt die Tabelle neu
     */
    private void reloadTable() {
        switch (tableViewStatus) {
            case STATUS_ACTIVE:
                showActiveBookings(new ActionEvent());
                break;
            case STATUS_DONE:
                showDoneBookings(new ActionEvent());
                break;
            case STATUS_ALL:
                showAllBookings(new ActionEvent());
                break;
        }
    }

    /**
     * Gibt die URL für die passende ReturnCarView zurück
     *
     * @return Die URL für den Typ des Fahrzeugs
     * @throws IOException Wenn die Datei nicht gefunden wird
     */
    @FXML
    private URL sceneChooser() throws IOException {
        Booking booking = getSelectedBooking();
        URL setURL = null;
        Car car = CarRepository.find(booking.getCarId());
        CarTypeEnum carType = car.getType();
        switch (carType) {
            case SUV:
                URL url = getClass().getResource("fxml/Booking-suv-return-view.fxml");
                setURL = url;
                break;
            case LKW:
                URL url1 = getClass().getResource("fxml/Booking-truck-return-view.fxml");
                setURL = url1;
                break;
            case BUS:
                URL url2 = getClass().getResource("fxml/Booking-bus-return-view.fxml");
                setURL = url2;
                break;
            case VAN:
                URL url3 = getClass().getResource("fxml/Booking-van-return-view.fxml");
                setURL = url3;
                break;
            case MOTORRAD:
                URL url4 = getClass().getResource("fxml/Booking-bike-return-view.fxml");
                setURL = url4;
                break;
            default:
                URL url5 = getClass().getResource("fxml/Booking-car-return-view.fxml");
                setURL = url5;
                break;
        }
        return setURL;
    }

    /**
     * Initialisiert die View
     * dies geschieht automatisch, wenn die View geladen wird
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewStatus = STATUS_ACTIVE;
        configureTableView();
        reloadTable();
    }
}
