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

/**
 * Controller für die Einstellungen
 */
@SuppressWarnings("MissingJavadoc")
public final class SettingsViewController implements Initializable {

    @FXML
    private Button saveButton;

    @FXML
    private ChoiceBox<String> languageSelection;

    /**
     * Speichert die Spracheinstellung und wechselt zur ersten Ansicht
     *
     * @param actionEvent Das Event
     * @throws IOException Wenn die Datei nicht gefunden werden kann
     */
    @FXML
    private void saveButtonAction(ActionEvent actionEvent) throws IOException {
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

    /**
     * Initialisiert die Sprachauswahl
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageSelection.getItems().addAll("English", "Deutsch");
        languageSelection.setValue("Deutsch");
    }
}
