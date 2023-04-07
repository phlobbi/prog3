package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
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

public class CarCreationController implements Initializable{

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
    void createCar(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            validateTextField(textFieldBrand, labelBrand.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldModel, labelModel.getText() + " " + bundle.getString("EMPTY"));
            if (datePickCreationDate.getValue() == null){
                throw new IllegalArgumentException(labelCreationDate.getText() + " " + bundle.getString("EMPTY"));
            }
            validateTextField(textFieldSeats, labelSeats.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldLicensePlate, labelLicensePlate.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldBasePrice, labelBasePrice.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldCurrentPrice, labelCurrentPrice.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldHorsePower, labelHorsePower.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldMileage, labelMileage.getText() + " " + bundle.getString("EMPTY"));

            String brand = textFieldBrand.getText();
            int seats = Integer.parseInt(textFieldSeats.getText());
            LocalDate creationDateLocal = datePickCreationDate.getValue();
            String model = textFieldModel.getText();
            double curPrice = Double.parseDouble(textFieldCurrentPrice.getText());
            double basePrice = Double.parseDouble(textFieldBasePrice.getText());
            String licensePlate = textFieldLicensePlate.getText();
            int horsePower = Integer.parseInt(textFieldHorsePower.getText());
            int mileage = Integer.parseInt(textFieldMileage.getText());

            // Werte bekommen vom Menü
            CarTypeEnum concreteType = CarTypeEnum.ANDERE;
            TransmissionTypeEnum concreteTransmission = TransmissionTypeEnum.MANUELL;
            FuelTypeEnum concreteFuel = FuelTypeEnum.BENZIN;
            boolean concreteSatNav = true;

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

            if (satNavStr.equals(bundle.getString("NEIN"))){
                    concreteSatNav = false;
            }

            // LocalDate vom DatePicker zu Calender-Format
            Date creationDate = Date.from(creationDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar creationDateCal = Calendar.getInstance();
            creationDateCal.setTime(creationDate);

            Car car = new Car(concreteType,brand,creationDateCal,seats,basePrice,curPrice,
                    licensePlate,model,horsePower,concreteTransmission,concreteFuel,concreteSatNav,mileage);

            CarRepository.persist(car);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CAR_CREATED"));
            alert.showAndWait();
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_CAR_CREATION"));
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
    }

    /**
     * Setzt gewollten Autotypen, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable carType mit dem neuen Typen ersetzt
     */
    private void setChosenCarType(){
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        carTypeStr = bundle.getString(CarTypeEnum.ANDERE.name());
        menuType.setText(carTypeStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (CarTypeEnum type : CarTypeEnum.values()){
            MenuItem item = new MenuItem(bundle.getString(type.name()));
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
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        transmissionStr = bundle.getString(TransmissionTypeEnum.MANUELL.name());
        menuTransmission.setText(transmissionStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()){
            MenuItem item = new MenuItem(bundle.getString(transmission.name()));
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
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        fuelStr = bundle.getString(FuelTypeEnum.BENZIN.name());
        menuFuel.setText(fuelStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (FuelTypeEnum fuel : FuelTypeEnum.values()){
            MenuItem item = new MenuItem(bundle.getString(fuel.name()));
            menuFuel.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e ->{
                menuFuel.setText(item.getText());
                fuelStr = item.getText();
            });
        }
    }

    /**
     * Setzt gewollte Navi-Verfügbarkeit, hierbei wird bei jedem
     * Klick auf ein Menü-Item der Text im Menü-Button ersetzt und
     * die lokale Variable satNav mit dem neuen Typen ersetzt
     */
    private void setChosenSatNav(){
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        satNavStr = bundle.getString("JA");
        menuSatNav.setText(satNavStr);

        MenuItem item = new MenuItem(bundle.getString("JA"));
        menuSatNav.getItems().add(item);
        MenuItem item2 = new MenuItem(bundle.getString("NEIN"));
        menuSatNav.getItems().add(item2);

        // wird Action auf Item bemerkt -> carType aktualisieren und Text
        item.setOnAction(e ->{
            menuSatNav.setText(item.getText());
            satNavStr = item.getText();
        });
        item2.setOnAction(e ->{
            menuSatNav.setText(item2.getText());
            satNavStr = item2.getText();
            System.out.println(satNavStr);
        });
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
