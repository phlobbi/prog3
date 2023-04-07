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

/**
 * Controller für die Bearbeitung eines Autos
 */
public class CarEditController implements Initializable {

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

    /**
     * Bricht die Bearbeitung ab und schließt das Fenster
     *
     * @param event Event
     */
    @FXML
    private void cancelCreation(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Schließt die Bearbeitung ab und speichert die Änderungen
     *
     * @param event Event
     */
    @FXML
    private void updateCar(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
            validateTextField(textFieldBrand, labelBrand.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldSeats, labelSeats.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldModel, labelModel.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldCurrentPrice, labelCurrentPrice.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldBasePrice, labelBasePrice.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldLicensePlate, labelLicensePlate.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldHorsePower, labelHorsePower.getText() + " " + bundle.getString("EMPTY"));
            validateTextField(textFieldMileage, labelMileage.getText() + " " + bundle.getString("EMPTY"));
            if (datePickCreationDate.getValue() == null) {
                throw new IllegalArgumentException(labelCreationDate.getText() + " " + bundle.getString("EMPTY"));
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
            boolean concreteSatNav = true;

            for (CarTypeEnum type : CarTypeEnum.values()) {
                if (type.getLabel().equals(carTypeStr)) {
                    concreteType = type;
                }
            }

            for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()) {
                if (transmission.getLabel().equals(transmissionStr)) {
                    concreteTransmission = transmission;
                }
            }

            for (FuelTypeEnum type : FuelTypeEnum.values()) {
                if (type.getLabel().equals(fuelStr)) {
                    concreteFuel = type;
                }
            }

            if (satNavStr.equals(bundle.getString("NEIN")) ){
                concreteSatNav = false;
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

            if (car.equals(temp)) {
                throw new IllegalArgumentException(bundle.getString("NO_CHANGES_MADE"));
            } else {
                CarRepository.persist(car);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, bundle.getString("CAR_UPDATED"));
                alert.showAndWait();
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_CAR_UPDATE"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Initialisiert die View
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
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
    private void setChosenCarType() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        carTypeStr = bundle.getString(CarTypeEnum.ANDERE.name());
        menuType.setText(carTypeStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (CarTypeEnum type : CarTypeEnum.values()) {
            MenuItem item = new MenuItem(bundle.getString(type.name()));
            menuType.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e -> {
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
    private void setChosenTransmission() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        transmissionStr = bundle.getString(TransmissionTypeEnum.MANUELL.name());
        menuTransmission.setText(transmissionStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (TransmissionTypeEnum transmission : TransmissionTypeEnum.values()) {
            MenuItem item = new MenuItem(bundle.getString(transmission.name()));
            menuTransmission.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e -> {
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
    private void setChosenFuel() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        fuelStr = bundle.getString(FuelTypeEnum.BENZIN.name());
        menuFuel.setText(fuelStr);

        // Iteration über alle Enums und für jedes 1 Menü-Item erstellen
        for (FuelTypeEnum fuel : FuelTypeEnum.values()) {
            MenuItem item = new MenuItem(bundle.getString(fuel.name()));
            menuFuel.getItems().add(item);
            // wird Action auf Item bemerkt -> carType aktualisieren und Text
            item.setOnAction(e -> {
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
    private void setChosenSatNav() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        // basistyp, falls kein Typ ausgewählt
        satNavStr = bundle.getString("JA");
        menuSatNav.setText(satNavStr);

        MenuItem item = new MenuItem(bundle.getString("JA"));
        menuSatNav.getItems().add(item);
        MenuItem item2 = new MenuItem(bundle.getString("NEIN"));
        menuSatNav.getItems().add(item2);

        // wird Action auf Item bemerkt -> carType aktualisieren und Text
        item.setOnAction(e -> {
            menuSatNav.setText(item.getText());
            satNavStr = item.getText();
        });
        item2.setOnAction(e -> {
            menuSatNav.setText(item2.getText());
            satNavStr = item2.getText();
            System.out.println(satNavStr);
        });
    }

    /**
     * Lädt das Auto aus der Klasse
     */
    @FXML
    private void loadCar() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.i18n");
        try {
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
            menuType.setText(bundle.getString(loadedCar.getType().name()));
            menuTransmission.setText(bundle.getString(loadedCar.getTransmissionType().name()));
            menuFuel.setText(bundle.getString(loadedCar.getFuelType().name()));
            menuSatNav.setText(bundle.getString(translateSatNavtoSatNavString(loadedCar.getSatNav()).toUpperCase()));
            carTypeStr = loadedCar.getType().getLabel();
            transmissionStr = loadedCar.getTransmissionType().getLabel();
            fuelStr = loadedCar.getFuelType().getLabel();
            satNavStr = translateSatNavtoSatNavString(loadedCar.getSatNav());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("MENU_ERROR"));
            alert.setHeaderText(bundle.getString("MENU_ERROR_CAR_LOAD"));
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Übersetzt den boolean-Wert von satNav in den String "Ja" bzw. "Nein",
     * der auf dem entsprechenden Menu-Eintrag der Navi-Auswahl angezeigt werden soll.
     *
     * @param satNav zu übersetzender boolean-Wert
     * @return der übersetzte String
     */
    private String translateSatNavtoSatNavString(boolean satNav) {
        return (satNav) ? "Ja" : "Nein";
    }

    /**
     * Überprüft Textfelder auf Gültigkeit
     *
     * @param textField    betreffendes Textfeld
     * @param errorMessage Fehlermeldung
     */
    private void validateTextField(TextField textField, String errorMessage) {
        if (textField.getText() == null || textField.getText().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}