package de.htwsaar.hopper.ui;


import de.htwsaar.hopper.repositories.CarRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import java.io.IOException;


public final class CarManagementController {

    private static int input;

    @FXML
    private Button btnRead;

    public static int getInput() {
        return input;
    }

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<?> tableView;

    @FXML
    void switchToSceneAddCar(ActionEvent event) {

    }

    /**
     * Diese Methode ermöglicht es Ihnen, ein genaues Auto zu lesen.
     * Ein Dialog wird angezeigt, in dem Sie die Informationen über das Auto eingeben können,
     * das gelesen werden soll.
     * @param event button click
     */
    @FXML
    void switchToSceneReadCar(ActionEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText("ID".toUpperCase());
        inputDialog.setContentText("Geben Sie bitte ein CarID ein");
        inputDialog.showAndWait().ifPresent(response -> {

            try {
                input = Integer.parseInt(inputDialog.getEditor().getText());
                if (CarRepository.find(input) != null) {
                    App.loadWindow("carshow.fxml");
                }
            } catch (IllegalArgumentException | IOException ex) {
                System.out.println("Falsche eingabe");
            }

        });

    }

    @FXML
    void switchToSceneRemoveCar(ActionEvent event) {

    }

    @FXML
    void switchToSceneUpdateCar(ActionEvent event) {

    }

    /**
     * Wechselt bei Aufruf auf die Startseite zurück.
     *
     * @param event button click
     */
    @FXML
    void switchToFirstView(ActionEvent event) throws IOException {
        App.setRoot("first-view.fxml");

    }

}


