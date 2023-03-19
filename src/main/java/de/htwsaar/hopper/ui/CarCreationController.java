package de.htwsaar.hopper.ui;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.implementations.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
        //  Fenster schließen oder zur CarView zurück
        App.setRoot("fxml/Car-view.fxml");
    }

    @FXML
    void createCar(ActionEvent event) {

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

        try {
            Car car = new Car(concreteType,brand,creationDateCal,seats,basePrice,curPrice,licensePlate,model);
            System.out.println(car.toString());
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setTitle("Fehler");
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
}
