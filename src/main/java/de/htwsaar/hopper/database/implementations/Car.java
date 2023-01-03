package de.htwsaar.hopper.database.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.interfaces.CarInterface;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Implementierung des CarInterface.
 * Annotiert fuer Datenbank.
 * @author gitroba
 */
@Entity
@Table(name = "Autos",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "AutoId"),
                @UniqueConstraint(columnNames = "Kennzeichen")})
public class Car implements CarInterface {

    @Id
    @Column(name = "AutoId")
    private int carId;

    @Column(name = "Typ")
    private CarTypeEnum type;

    @Column(name = "Marke")
    private String brand;

    @Column(name = "Erstellungsdatum")
    private Date creationDate;

    @Column(name = "Sitze")
    private int seats;

    @Column(name = "Grundpreis")
    private double basePrice;

    @Column(name = "Tagespreis")
    private double currentPrice;

    @Column(name = "Kennzeichen")
    private String licensePlate;

    @Column(name = "Modell")
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
    public Car(int carId, CarTypeEnum type, String brand, Date creationDate, int seats, double basePrice,
               double currentPrice, String licensePlate, String model) {
        this.carId = carId;
        this.type = type;
        this.brand = brand;
        this.creationDate = creationDate;
        this.seats = seats;
        this.basePrice = basePrice;
        this.currentPrice = currentPrice;
        this.licensePlate = licensePlate;
        this.model = model;
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
        this.brand = brand;
    }

    /**
     * Getter fuer das Erstellungsdatum.
     * @return Das Erstellungsdatum.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Setter fuer das Erstellungsdatum.
     * @param creationDate Das Erstellungsdatum.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
        this.seats = seats;
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
        this.basePrice = basePrice;
    }

    /**
     * Getter fuer den Tagespreis.
     * @return Der Tagespreis.
     */
    public double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Setter fuer den Tagespreis.
     * @param currentPrice Der Tagespreis.
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
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
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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
        this.model = model;
    }

    /**
     * Equals-Methode.
     * @param o Das zu vergleichende Objekt.
     * @return Ergebnis des Vergleiches.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && seats == car.seats && basePrice == car.basePrice && currentPrice == car.currentPrice && Objects.equals(type, car.type) && Objects.equals(brand, car.brand) && Objects.equals(creationDate, car.creationDate) && Objects.equals(licensePlate, car.licensePlate) && Objects.equals(model, car.model);
    }

    /*+
     * HashCode-Methode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(carId, type, brand, creationDate, seats, basePrice, currentPrice, licensePlate, model);
    }
}
