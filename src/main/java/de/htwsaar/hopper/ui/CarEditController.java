package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.SatNavEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
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
    private String transmissionStr;
    private String fuelStr;
    private String satNavStr;

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
    private Label labelHorsePower;

    @FXML
    private Label labelTransmissionType;

    @FXML
    private Label labelFuelType;

    @FXML
    private Label labelSatNav;

    @FXML
    private Label labelMileage;

    @FXML
    private MenuButton menuType;

    @FXML
    private MenuButton menuTransmission;

    @FXML
    private MenuButton menuFuel;

    @FXML
    private MenuButton menuSatNav;

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
    private TextField textFieldHorsePower;

    @FXML
    private TextField textFieldMileage;

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
            validateTextField(textFieldHorsePower, labelHorsePower.getText() + "leer");
            validateTextField(textFieldMileage, labelMileage.getText() + "leer");
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
            int horsePower = Integer.parseInt(textFieldHorsePower.getText());
            int mileage = Integer.parseInt(textFieldMileage.getText());

            // Enum bekommen vom Menü
            CarTypeEnum concreteType = CarTypeEnum.ANDERE;
            TransmissionTypeEnum concreteTransmission = TransmissionTypeEnum.MANUELL;
            FuelTypeEnum concreteFuel = FuelTypeEnum.BENZIN;
            SatNavEnum concreteSatNav= SatNavEnum.JA;

            for (CarTypeEnum type : CarTypeEnum.values()){
                if (type.getLabel().equals(carTypeStr)){
                    concreteType = type;
                }
            }

            for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()){
                if (transmission.getLabel().equals(transmissionStr)){
                    concreteTransmission = transmission;
                }
            }

            for (FuelTypeEnum type : FuelTypeEnum.values()){
                if (type.getLabel().equals(fuelStr)){
                    concreteFuel = type;
                }
            }

            for (SatNavEnum type : SatNavEnum.values()){
                if (type.getLabel().equals(satNavStr)){
                    concreteSatNav = type;
                }
            }
            // LocalDate vom DatePicker zu Calender-Format
            Date creationDate = Date.from(creationDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar creationDateCal = Calendar.getInstance();
            creationDateCal.setTime(creationDate);

            Car car = CarManagementController.getSelectedCar();

            Car temp = new Car(car.getType(), car.getBrand(), car.getCreationDate(), car.getSeats(),
                    car.getBasePrice(), car.getCurrentPrice(), car.getLicensePlate(), car.getModel(),
                    car.getHorsepower(), car.getTransmissionType(), car.getFuelType(), car.getSatNav(), car.getMileage());


            car.setBrand(brand);
            car.setSeats(seats);
            car.setCreationDate(creationDateCal);
            car.setModel(model);
            car.setCurrentPrice(curPrice);
            car.setBasePrice(basePrice);
            car.setLicensePlate(licensePlate);
            car.setType(concreteType);
            car.setHorsepower(horsePower);
            car.setTransmissionType(concreteTransmission);
            car.setFuelType(concreteFuel);
            car.setSatNav(concreteSatNav);
            car.setMileage(mileage);

            if (car.equals(temp)){
                throw new IllegalArgumentException("Keine Änderungen vorgenommen");
            } else {
                CarRepository.persist(car);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Auto erfolgreich geandert!");
                alert.showAndWait();
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }

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
        setChosenTransmission();
        setChosenFuel();
        setChosenSatNav();
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

    /**
     * Setzt gewollte Getriebeart, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable transmission mit dem neuen Typen ersetzt
     */
    private void setChosenTransmission(){
        // basistyp, falls kein Typ ausgewählt
        transmissionStr = TransmissionTypeEnum.MANUELL.getLabel();
        menuTransmission.setText(transmissionStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()){
            MenuItem item = new MenuItem(transmission.getLabel());
            menuTransmission.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuTransmission.setText(item.getText());
                transmissionStr = item.getText();
            });
        }
    }

    /**
     * Setzt gewollten Kraftstoff, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable fuel mit dem neuen Typen ersetzt
     */
    private void setChosenFuel(){
        // basistyp, falls kein Typ ausgewählt
        fuelStr = FuelTypeEnum.BENZIN.getLabel();
        menuFuel.setText(fuelStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (FuelTypeEnum fuel : FuelTypeEnum.values()){
            MenuItem item = new MenuItem(fuel.getLabel());
            menuFuel.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuFuel.setText(item.getText());
                fuelStr = item.getText();
            });
        }
    }

    /**
     * Setzt gewollten Navi-Verfügbarkeit, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable satNav mit dem neuen Typen ersetzt
     */
    private void setChosenSatNav(){
        // basistyp, falls kein Typ ausgewählt
        satNavStr = SatNavEnum.JA.getLabel();
        menuSatNav.setText(satNavStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (SatNavEnum satNav : SatNavEnum.values()){
            MenuItem item = new MenuItem(satNav.getLabel());
            menuSatNav.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuSatNav.setText(item.getText());
                satNavStr = item.getText();
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
            textFieldHorsePower.setText(String.valueOf(loadedCar.getHorsepower()));
            textFieldMileage.setText(String.valueOf(loadedCar.getMileage()));
            menuType.setText(loadedCar.getType().getLabel());
            menuTransmission.setText(loadedCar.getTransmissionType().getLabel());
            menuFuel.setText(loadedCar.getFuelType().getLabel());
            menuSatNav.setText(loadedCar.getSatNav().getLabel());
            carTypeStr = loadedCar.getType().getLabel();
            transmissionStr = loadedCar.getTransmissionType().getLabel();
            fuelStr = loadedCar.getFuelType().getLabel();
            satNavStr = loadedCar.getSatNav().getLabel();
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