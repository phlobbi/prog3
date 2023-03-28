package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingCarChooseController implements Initializable {

    private Car chosenCar;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnResetSearch;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Car, String> carBasePriceColumn;

    @FXML
    private TableColumn<Car, String> carBrandColumn;

    @FXML
    private TableColumn<Car, String> carIdColumn;

    @FXML
    private TableColumn<Car, String> carModelColumn;

    @FXML
    private TableColumn<Car, String> carTypeColumn;

    @FXML
    private TableColumn<Car, String> carFuelTypeColumn;

    @FXML
    private TableColumn<Car, String> carTransmissionTypeColumn;

    @FXML
    private TableColumn<Car, String> carSatNavColumn;

    @FXML
    private TableColumn<Car, String> carHorsepowerColumn;

    @FXML
    private MenuButton menuButtonCriteria;

    @FXML
    private MenuItem menuItemUncheck;

    @FXML
    private CheckMenuItem searchCritBrand;

    @FXML
    private CheckMenuItem searchCritModel;

    @FXML
    private CheckMenuItem searchCritType;

    @FXML
    private CheckMenuItem searchCritFuelType;

    @FXML
    private CheckMenuItem searchCritTransmissionType;

    @FXML
    private TableView<Car> tableView;

    @FXML
    private TextField textFieldSearch;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Speichert das ausgeählte Auto
     * @param event Event
     */
    @FXML
    void saveChosenCar(ActionEvent event) {
        chosenCar = tableView.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    /**
     * Sucht nach ausgewählten Autos anhand der Filterkriterien
     * @param event Event
     */
    @FXML
    void searchForCar(ActionEvent event) {
        try{
            String searchCriteria = textFieldSearch.getText();

            if(searchCriteria.trim().isEmpty()){
                throw new IllegalArgumentException("Kein Suchkriterium eingegeben");
            }

            ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();
            checkMenuItems = getAllSelectedCriteria();

            if (checkMenuItems.isEmpty())
                throw new IllegalArgumentException("Kein Kriterium ausgewählt");


            tableView.getItems().clear();
            for (Car car : CarRepository.findAvailable()) {
                for (CheckMenuItem item : checkMenuItems) {
                    boolean allowedToInsert = false;
                    if (item.equals(searchCritBrand)) {
                        if (car.getBrand().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(searchCritModel)) {
                        if (car.getModel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(searchCritType)) {
                        if (car.getType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(searchCritFuelType)) {
                        if (car.getFuelType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    } else if (item.equals(searchCritTransmissionType)) {
                        if (car.getTransmissionType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            allowedToInsert = true;
                    }
                    if (!IsCarAlreadyInTable(car)){
                        if (allowedToInsert)
                            tableView.getItems().add(car);
                    }

                }
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler bei der Suche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Sucht nach Autos, wenn die Enter-Taste gedrückt wird
     * @param event Event
     */
    @FXML
    void searchForCarViaEnter(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            searchForCar(new ActionEvent());
        }
    }

    /**
     * Speichert das ausgewählte Auto durch Drücken der Enter-Taste
     * @param event
     */
    @FXML
    void saveCarViaEnter(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            saveChosenCar(new ActionEvent());
        }
    }

    /**
     * Entfernt die Auswahl aller Filterkriterien
     * @param event Event
     */
    @FXML
    void uncheckCriteria(ActionEvent event) {
        searchCritBrand.setSelected(false);
        searchCritModel.setSelected(false);
        searchCritType.setSelected(false);
        searchCritTransmissionType.setSelected(false);
        searchCritFuelType.setSelected(false);
    }

    /**
     * Setzt die Suche zurück, sodass keine Filterkriterien mehr aktiviert sind,
     * die Tabelle wieder auf original zurückgesetzt wird und das Suchfeld geleert wird
     * @param event Event
     */
    @FXML
    void resetSearch(ActionEvent event) {
        uncheckCriteria(new ActionEvent());
        reloadTable();
        textFieldSearch.clear();
    }

    /**
     * Lädt die Tabelle mit allen verfügbaren Autos
     */
    public void reloadTable(){
        tableView.getItems().clear();

        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        carTransmissionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transmissionType"));
        carFuelTypeColumn.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        carHorsepowerColumn.setCellValueFactory(new PropertyValueFactory<>("horsepower"));
        carSatNavColumn.setCellValueFactory(new PropertyValueFactory<>("satNavShowField"));
        carBasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        ObservableList<Car> observableList = FXCollections.observableArrayList();
        observableList.addAll(CarRepository.findAvailable());

        tableView.getItems().addAll(observableList);
        tableView.getSelectionModel().selectFirst();
        if (tableView.getSelectionModel().isEmpty()) {
            btnSave.setDisable(true);
            btnSearch.setDisable(true);
            btnCancel.setDisable(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadTable();
    }

    public Car getChosenCar(){
        return chosenCar;
    }

    /**
     * Gibt eine Liste aus mit allen Suchkriterien, die ausgewählt sind
     * @return Liste mit allen ausgewählten Suchkriterien
     */
    private ObservableList<CheckMenuItem> getAllSelectedCriteria(){
        ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();

        if (searchCritBrand.isSelected())
            checkMenuItems.add(searchCritBrand);
        if (searchCritModel.isSelected())
            checkMenuItems.add(searchCritModel);
        if (searchCritType.isSelected())
            checkMenuItems.add(searchCritType);
        if (searchCritTransmissionType.isSelected())
            checkMenuItems.add(searchCritTransmissionType);
        if (searchCritFuelType.isSelected())
            checkMenuItems.add(searchCritFuelType);

       return checkMenuItems;
    }

    /**
     * Prüft ob das Auto bereits in der Liste ist
     * @param car Auto was zu prüfen ist
     * @return true wenn Auto bereits in der Liste ist, sonst false
     */
    private boolean IsCarAlreadyInTable(Car car){
        return tableView.getItems().contains(car);
    }

}
