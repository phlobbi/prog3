package de.htwsaar.hopper.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable {

    @FXML
    public Button saveButton;

    @FXML
    public ChoiceBox<String> languageSelection;

    @FXML
    public void saveButtonAction(ActionEvent actionEvent) throws IOException {
        System.out.println(languageSelection.getValue());
        switch (languageSelection.getValue()) {
            case "English":
                Locale.setDefault(Locale.ENGLISH);
                break;
            case "Deutsch":
                Locale.setDefault(Locale.GERMAN);
                break;
        }
        App.setRoot("fxml/first-view.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageSelection.getItems().addAll("English", "Deutsch");
        languageSelection.setValue("Deutsch");
    }
}
