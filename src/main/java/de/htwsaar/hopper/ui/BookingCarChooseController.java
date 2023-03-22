package de.htwsaar.hopper.ui;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
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
import java.util.Arrays;
import java.util.ResourceBundle;

public class BookingCarChooseController implements Initializable {

    private Car chosenCar;
    @FXML
    private Button btnCancel;

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
    private TableView<Car> tableView;

    @FXML
    private TextField textFieldSearch;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChosenCar(ActionEvent event) {
        chosenCar = tableView.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    void searchForCar(ActionEvent event) {
        try{
            String searchCriteria = textFieldSearch.getText();

            ObservableList<CheckMenuItem> checkMenuItems = FXCollections.observableArrayList();
            checkMenuItems.addAll(Arrays.asList(searchCritBrand, searchCritModel, searchCritType));

            for(CheckMenuItem item : checkMenuItems)
                if(!item.isSelected())
                    checkMenuItems.remove(item);

            if (checkMenuItems.isEmpty())
                throw new IllegalArgumentException("Kein Kriterium ausgewählt");

            tableView.getItems().clear();
            for (Car car : CarRepository.findAvailable()) {
                for (CheckMenuItem item : checkMenuItems) {
                    if (item.equals(searchCritBrand)) {
                        if (car.getBrand().toLowerCase().contains(searchCriteria.toLowerCase()))
                            tableView.getItems().add(car);
                    } else if (item.equals(searchCritModel)) {
                        if (car.getModel().toLowerCase().contains(searchCriteria.toLowerCase()))
                            tableView.getItems().add(car);
                    } else if (item.equals(searchCritType)) {
                        if (car.getType().getLabel().toLowerCase().contains(searchCriteria.toLowerCase()))
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

    @FXML
    void searchForCarViaEnter(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")){
            searchForCar(new ActionEvent());
        }
    }


    @FXML
    void uncheckCriteria(ActionEvent event) {
        searchCritBrand.setSelected(false);
        searchCritModel.setSelected(false);
        searchCritType.setSelected(false);
    }

    public void reloadTable(){
        tableView.getItems().clear();

        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
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

}
