package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.interfaces.CarInterface;
import de.htwsaar.hopper.logic.validations.CarValidation;
import de.htwsaar.hopper.logic.validations.PreventNullPersistForCar;

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
    @Enumerated(EnumType.STRING)
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
        this.type = CarValidation.validateCarType(type);
        this.brand = CarValidation.validateString(brand, "Die Automarke darf nicht leer sein.");
        this.creationDate = CarValidation.validateCreatedDate(creationDate);
        this.seats = CarValidation.validateSeats(seats);
        this.basePrice = CarValidation.validateBasePrice(basePrice);
        this.currentPrice = CarValidation.validateCurrentPrice(currentPrice);
        this.licensePlate = CarValidation.validateLicensePlate(licensePlate);
        this.model = CarValidation.validateString(model, "Das Automodell darf nicht leer sein.");
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
        this.type = CarValidation.validateCarType(type);
    }

    @Override
    public void setBrand(String brand) {
        this.brand = CarValidation.validateString(brand, "Die Automarke darf nicht leer sein.");
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
        this.model = CarValidation.validateString(model,"Kein gültiges Modell!");
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", type=" + type +
                ", brand='" + brand + '\'' +
                ", creationDate=" + creationDate +
                ", seats=" + seats +
                ", basePrice=" + basePrice +
                ", currentPrice=" + currentPrice +
                ", licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        //if (getCarId() != car.getCarId()) return false;
        if (getSeats() != car.getSeats()) return false;
        if (Double.compare(car.getBasePrice(), getBasePrice()) != 0) return false;
        if (Double.compare(car.getCurrentPrice(), getCurrentPrice()) != 0) return false;
        if (getType() != car.getType()) return false;
        if (!getBrand().equals(car.getBrand())) return false;
        if (!getCreationDate().equals(car.getCreationDate())) return false;
        if (!getLicensePlate().equals(car.getLicensePlate())) return false;
        return getModel().equals(car.getModel());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getCarId();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getBrand().hashCode();
        result = 31 * result + getCreationDate().hashCode();
        result = 31 * result + getSeats();
        temp = Double.doubleToLongBits(getBasePrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getCurrentPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getLicensePlate().hashCode();
        result = 31 * result + getModel().hashCode();
        return result;
    }
}
