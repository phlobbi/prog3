package de.htwsaar.hopper;

import de.htwsaar.hopper.ui.App;

/**
 * Eine Hilfsklasse, die die JavaFX Applikation startet.
 */
public final class Start {

    /**
     * Privater Konstruktor, damit die Klasse nicht instanziiert werden kann.
     */
    private Start() {
    }

    /**
     * Einstiegspunkt für die Anwendung.
     * Die Methode ruft die main-Methode der Klasse App auf.
     * Das wurde so gemacht, da der Start der Anwendung über die main-Methode der Klasse App nicht funktioniert hat.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        App.main(args);
    }
}
