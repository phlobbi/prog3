package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.interfaces.CarInterface;
import de.htwsaar.hopper.logic.validations.CarValidation;
import de.htwsaar.hopper.logic.validations.PreventNullPersistForCar;
import de.htwsaar.hopper.logic.validations.Validation;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Implementierung des CarInterface.
 * Annotiert fuer die Datenbank.
 * @author gitroba
 */
@EntityListeners(PreventNullPersistForCar.class)
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
     * Standard-Konstruktor
     */
    public Car() {
    }

    /**
     * Konstruktor mit allen Werten.
     * @param type Typ des Autos
     * @param brand Marke des Autos
     * @param creationDate Herstellungsdatum des Autos
     * @param seats Sitzanzahl des Autos
     * @param basePrice Grundpreis des Autos
     * @param currentPrice Tagespreis des Autos
     * @param licensePlate Kennzeichen des Autos
     * @param model Modell des Autos
     */
    public Car(CarTypeEnum type, String brand, Calendar creationDate, int seats, double basePrice,
               double currentPrice, String licensePlate, String model) {
        this.type = Validation.validateCarType(type);
        this.brand = Validation.validateString(brand, "Die Automarke darf nicht leer sein.");
        this.creationDate = CarValidation.validateCreatedDate(creationDate);
        this.seats = CarValidation.validateSeats(seats);
        this.basePrice = CarValidation.validateBasePrice(basePrice);
        this.currentPrice = CarValidation.validateCurrentPrice(currentPrice);
        this.licensePlate = CarValidation.validateLicensePlate(licensePlate);
        this.model = Validation.validateString(model, "Das Automodell darf nicht leer sein.");
    }

    /* GETTER */
    @Override
    public int getCarId() {
        return carId;
    }

    @Override
    public CarTypeEnum getType() {
        return type;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public Calendar getCreationDate() {
        return creationDate;
    }

    @Override
    public int getSeats() {
        return seats;
    }

    @Override
    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String getModel() {
        return model;
    }

    /* SETTER */
    @Override
    public void setType(CarTypeEnum type) {
        this.type = Validation.validateCarType(type);
    }

    @Override
    public void setBrand(String brand) {
        this.brand = Validation.validateString(brand, "Die Automarke darf nicht leer sein.");
    }

    @Override
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = CarValidation.validateCreatedDate(creationDate);
    }

    @Override
    public void setSeats(int seats) {
        this.seats = CarValidation.validateSeats(seats);
    }

    @Override
    public void setBasePrice(double basePrice) {
        this.basePrice = CarValidation.validateBasePrice(basePrice);
    }

    @Override
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = CarValidation.validateCurrentPrice(currentPrice);
    }

    @Override
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = CarValidation.validateLicensePlate(licensePlate);
    }

    @Override
    public void setModel(String model) {
        this.model = Validation.validateString(model,"Kein gültiges Modell!");
    }
}
