/**
 * 
 */
/**
 * @author Acer
 *
 */
module de.htwsaar.hopper.ui {
	exports de.htwsaar.hopper.ui;
	exports de.htwsaar.hopper.logic.implementations;
	exports de.htwsaar.hopper.logic.enums;
	exports de.htwsaar.hopper;
	exports de.htwsaar.hopper.logic.interfaces;

	requires java.persistence;
	requires javafx.graphics;
	requires javafx.controls ;
	requires javafx.fxml ;
	opens de.htwsaar.hopper.ui to javafx.graphics ;
}