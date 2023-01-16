package de.htwsaar.hopper.ui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class App extends Application {
	private static Parent root ;
	private static Scene scene ;

	/**
	 * Startet die JavaFX Applikation.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
			URL url = App.class.getResource("/de/htwsaar/hopper/ui/first-view.fxml");
		if (url != null) {
			root = FXMLLoader.load(url);
			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} else {
			throw new IOException("Could not load FXML file.");
		}
	}

	/**
	 * Die Methode erlaubt den Wechsel zwischen Scenes.
	 * @param fxmlfile Pfad zur FXML-Datei, die geladen werden soll
	 */
	public static void setRoot(String fxmlfile)   {
		try {
			URL url = App.class.getResource("/"+fxmlfile+"")  ;
			root = FXMLLoader.load(url) ;
			scene.setRoot(root);
		} catch (IOException exception){
			System.out.println("Die fxml-Datei konnte nicht geladen werden");
		}
	}

	/**
	 * Die Methode startet die Applikation.
	 */
	public static void main(String[] args) {
	    launch(args);	
	}

}
