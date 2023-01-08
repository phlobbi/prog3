package de.htwsaar.hopper.ui;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	private static Parent root ;
	private static Scene scene ;

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = App.class.getResource("/firstview.fxml") ;
		//	System.out.println(url.toString()) ;
		root = FXMLLoader.load(url) ;
		scene = new Scene(root) ;
		primaryStage.setScene(scene);
		
		primaryStage.show();
		 
		
	}
	public static void setRoot(String fxmlfile) throws IOException {
		URL url = App.class.getResource("/"+fxmlfile+"") ;
		//	System.out.println(url.toString()) ;
		root = FXMLLoader.load(url) ;
		scene.setRoot(root);

	}
	
	public static void main(String[] args) {
	    launch(args);	
	}
	

}
