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

public class CarCreationController implements Initializable{

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
    void createCar(ActionEvent event) {
        try {
            validateTextField(textFieldBrand, labelBrand.getText() + " leer");
            validateTextField(textFieldModel, labelModel.getText() + " leer");
            if (datePickCreationDate.getValue() == null){
                throw new IllegalArgumentException(labelCreationDate.getText() + " leer");
            }
            validateTextField(textFieldSeats, labelSeats.getText() + " leer");
            validateTextField(textFieldLicensePlate, labelLicensePlate.getText() + " leer");
            validateTextField(textFieldBasePrice, labelBasePrice.getText() + " leer");
            validateTextField(textFieldCurrentPrice, labelCurrentPrice.getText() + " leer");

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
            
            Car car = new Car(concreteType,brand,creationDateCal,seats,basePrice,curPrice,licensePlate,model);

            CarRepository.persist(car);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Auto erfolgreich erstellt");
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
