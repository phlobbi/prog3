package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.interfaces.CarInterface;
import de.htwsaar.hopper.logic.validations.Validation;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Implementierung des CarInterface.
 * Annotiert fuer Datenbank.
 * @author gitroba
 */
@Entity
@Table(name = "Cars",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "CarId"),
                @UniqueConstraint(columnNames = "LicensePlate")})
public class Car implements CarInterface {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CarId")
    private int carId;

    @Basic
    @Column(name = "Type")
    private CarTypeEnum type;

    @Basic
    @Column(name = "Brand")
    private String brand;

    @Basic
    @Column(name = "CreationDate")
    @Temporal(TemporalType.DATE)
    private Calendar creationDate;

    @Basic
    @Column(name = "Seats")
    private int seats;

    @Basic
    @Column(name = "BasePrice")
    private double basePrice;

    @Basic
    @Column(name = "CurrentPrice")
    private double currentPrice;

    @Basic
    @Column(name = "LicensePlate")
    private String licensePlate;

    @Basic
    @Column(name = "Model")
    private String model;

    /**
     * Standard-Konstruktor.
     */
    public Car() {
    }

    /**
     * Konstruktor mit allen Werten.
     * @param carId Die Id.
     * @param type Der Typ.
     * @param brand Die Marke.
     * @param creationDate Das Erstellungsdatum.
     * @param seats Die Sitzanzahl.
     * @param basePrice Der Grundpreis.
     * @param currentPrice Der Tagespreis.
     * @param licensePlate Das Kennzeichen.
     * @param model Das Modell.
     */
    public Car(int carId, CarTypeEnum type, String brand, Calendar creationDate, int seats, double basePrice,
               double currentPrice, String licensePlate, String model) {
        this.carId = carId;
        this.type = type;
        this.brand = Validation.validateString(brand, "Die Automarke darf nicht leer sein.");
        this.creationDate = Validation.validateDate(creationDate);
        this.seats = Validation.validateSeats(seats);
        this.basePrice = Validation.validateBasePrice(basePrice);
        this.currentPrice = Validation.validateCurrentPrice(currentPrice);
        this.licensePlate = Validation.validateLicensePlate(licensePlate);
        this.model = Validation.validateString(model, "Das Automodell darf nicht leer sein.");
    }

    /**
     * Getter für CarId.
     *
     * @return CarId Die ID (Schluessel).
     */
    public int getCarId() {
        return carId;
    }

    /**
     * Setter fuer CarId.
     * @param carId Die ID (Schluessel).
     */
    public void setCarId(int carId) {
        this.carId = carId;
    }

    /**
     * Getter fuer enum, die die Autotypen enthaelt.
     * @return CarTypeEnum Der Typ.
     */
    public CarTypeEnum getType() {
        return type;
    }

    /**
     * Setter fuer die Autotyp-enum.
     * @param type Der Typ des Autos.
     */
    public void setType(CarTypeEnum type) {
        this.type = type;
    }

    /**
     * Getter fuer die Marke.
     * @return Die Marke.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Setter fuer die Marke.
     * @param brand Die Marke.
     */
    public void setBrand(String brand) {
        this.brand = Validation.validateString(brand, "Die Automarke darf nicht leer sein.");
    }

    /**
     * Getter fuer das Erstellungsdatum.
     * @return Das Erstellungsdatum.
     */
    public Calendar getCreationDate() {
        return creationDate;
    }

    /**
     * Setter fuer das Erstellungsdatum.
     * @param creationDate Das Erstellungsdatum.
     */
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = Validation.validateDate(creationDate);
    }

    /**
     * Getter fuer die Anzahl der Sitze.
     * @return Die Sitzanzahl.
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Setter fuer die Anzahl der Sitze.
     * @param seats Die Sitzanzahl.
     */
    public void setSeats(int seats) {
        this.seats = Validation.validateSeats(seats);
    }

    /**
     * Getter fuer den Grundpreis.
     * @return Der Grundpreis.
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Setter fuer den Grundpreis.
     * @param basePrice Der Grundpreis.
     */
    public void setBasePrice(double basePrice) {
        this.basePrice = Validation.validateBasePrice(basePrice);
    }

    /**
     * Getter fuer den Tagespreis.
     * @return Der Tagespreis.
     */
    public double getCurrentPrice() {
        return Validation.validateCurrentPrice(currentPrice);
    }

    /**
     * Setter fuer den Tagespreis.
     * @param currentPrice Der Tagespreis.
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = Validation.validateCurrentPrice(currentPrice);
    }

    /**
     * Getter fuer das Kennzeichen.
     * @return Das Kennzeichen.
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Setter fuer das Kennzeichen.
     * @param licensePlate Das Kennzeichen.
     */
    public void setLicensePlate(String licensePlate) { // mit regex prüfen ob Nummernschild existiert???
        this.licensePlate = Validation.validateLicensePlate(licensePlate);
    }

    /**
     * Getter fuer das Modell.
     * @return Das Modell.
     */
    public String getModel() {
        return model;
    }

    /**
     * Setter fuer das Modell.
     * @param model Das Modell.
     */
    public void setModel(String model) {
        this.model = Validation.validateString(model,"Kein gültiges Modell!");
    }

}
