package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;
import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class CarEditController implements Initializable{

    private String carTypeStr;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelBasePrice;

    @FXML
    private Label labelBrand;

    @FXML
    private Label labelCreationDate;

    @FXML
    private Label labelCurrentPrice;

    @FXML
    private Label labelLicensePlate;

    @FXML
    private Label labelModel;

    @FXML
    private Label labelSeats;

    @FXML
    private Label labelType;

    @FXML
    private MenuButton menuType;

    @FXML
    private TextField textFieldBasePrice;

    @FXML
    private TextField textFieldBrand;

    @FXML
    private DatePicker datePickCreationDate;

    @FXML
    private TextField textFieldCurrentPrice;

    @FXML
    private TextField textFieldLicensePlate;

    @FXML
    private TextField textFieldModel;

    @FXML
    private TextField textFieldSeats;

    @FXML
    void cancelCreation(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateCar(ActionEvent event) {
        try {
            validateTextField(textFieldBrand, labelBrand.getText() + " leer");
            validateTextField(textFieldSeats, labelSeats.getText() + " leer");
            validateTextField(textFieldModel, labelModel.getText() + " leer");
            validateTextField(textFieldCurrentPrice, labelCurrentPrice.getText() + " leer");
            validateTextField(textFieldBasePrice, labelBasePrice.getText() + " leer");
            validateTextField(textFieldLicensePlate, labelLicensePlate.getText() + " leer");
            if (datePickCreationDate.getValue() == null){
                throw new IllegalArgumentException(labelCreationDate.getText() + " leer");
            }

            String brand = textFieldBrand.getText();
            int seats = Integer.parseInt(textFieldSeats.getText());
            LocalDate creationDateLocal = datePickCreationDate.getValue();
            String model = textFieldModel.getText();
            double curPrice = Double.parseDouble(textFieldCurrentPrice.getText());
            double basePrice = Double.parseDouble(textFieldBasePrice.getText());
            String licensePlate = textFieldLicensePlate.getText();

            // Enum bekommen vom Menü
            CarTypeEnum concreteType = CarTypeEnum.ANDERE;

            for (CarTypeEnum type : CarTypeEnum.values()){
                if (type.getLabel().equals(carTypeStr)){
                    concreteType = type;
                }
            }

            // LocalDate vom DatePicker zu Calender-Format
            Date creationDate = Date.from(creationDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar creationDateCal = Calendar.getInstance();
            creationDateCal.setTime(creationDate);

            Car car = CarManagementController.getSelectedCar();

            Car temp = new Car(car.getType(), car.getBrand(), car.getCreationDate(), car.getSeats(),
                    car.getBasePrice(), car.getCurrentPrice(), car.getLicensePlate(), car.getModel());


            car.setBrand(brand);
            car.setSeats(seats);
            car.setCreationDate(creationDateCal);
            car.setModel(model);
            car.setCurrentPrice(curPrice);
            car.setBasePrice(basePrice);
            car.setLicensePlate(licensePlate);
            car.setType(concreteType);
            System.out.println(car.equals(temp));

                if (car.equals(temp)){
                throw new IllegalArgumentException("Keine Änderungen vorgenommen");
            } else {
                CarRepository.update(car);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Auto erfolgreich geandert!");
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Erstellen des Autos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChosenCarType();
        loadCar();
    }

    /**
     * Setzt gewollten Autotypen, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable carType mit dem neuen Typen ersetzt
     */
    private void setChosenCarType(){
        // basistyp, falls kein Typ ausgewählt
        carTypeStr = CarTypeEnum.ANDERE.getLabel();
        menuType.setText(carTypeStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (CarTypeEnum type : CarTypeEnum.values()){
            MenuItem item = new MenuItem(type.getLabel());
            menuType.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuType.setText(item.getText());
                carTypeStr = item.getText();
            });
        }
    }

    @FXML
    void loadCar() {
        try{
            CarManagementController carManagerController = new CarManagementController();
            Car loadedCar = CarManagementController.getSelectedCar();
            textFieldBrand.setText(loadedCar.getBrand());
            textFieldSeats.setText(String.valueOf(loadedCar.getSeats()));
            datePickCreationDate.setValue(loadedCar.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            textFieldModel.setText(loadedCar.getModel());
            textFieldCurrentPrice.setText(String.valueOf(loadedCar.getCurrentPrice()));
            textFieldBasePrice.setText(String.valueOf(loadedCar.getBasePrice()));
            textFieldLicensePlate.setText(loadedCar.getLicensePlate());
            menuType.setText(loadedCar.getType().getLabel());
            carTypeStr = loadedCar.getType().getLabel();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Laden des Autos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Überprüft Textfelder auf Gültigkeit
     * @param textField betreffendes Textfeld
     * @param errorMessage Fehlermeldung
     */
    private void validateTextField(TextField textField, String errorMessage){
        if(textField.getText() == null || textField.getText().isEmpty()){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}