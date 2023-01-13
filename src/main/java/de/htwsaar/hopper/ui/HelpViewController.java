package de.htwsaar.hopper.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class HelpViewController {

    @FXML
    private Button btnHelp;

    @FXML
    private BorderPane root;

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     */
    @FXML
    void switchToFirstView(ActionEvent event)  {
        App.setRoot("First-view.fxml");

    }

}
