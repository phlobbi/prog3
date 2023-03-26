package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.enums.FuelTypeEnum;
import de.htwsaar.hopper.logic.enums.SatNavEnum;
import de.htwsaar.hopper.logic.enums.TransmissionTypeEnum;
import de.htwsaar.hopper.logic.interfaces.CarInterface;
import de.htwsaar.hopper.logic.validations.CarValidation;
import de.htwsaar.hopper.logic.validations.PreventNullPersistForCar;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

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

    @Basic
    @Column(name = "Horsepower")
    private int horsepower;

    @Basic
    @Column(name = "TransmissionType")
    private TransmissionTypeEnum transmissionType;

    @Basic
    @Column(name = "FuelType")
    private FuelTypeEnum fuelType;

    @Basic
    @Column(name = "SatNav")
    private SatNavEnum satNav;

    @Basic
    @Column(name = "Mileage")
    private int mileage;

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
     * @param horsepower Pferdstärke des Autos
     * @param transmissionType Schaltgetriebe des Autos
     * @param fuelType Kraftstoff des Autos
     * @param satNav integriertes Navi im Autos
     * @param mileage Kilometerstand des Autos
     */
    public Car(CarTypeEnum type, String brand, Calendar creationDate, int seats, double basePrice,
               double currentPrice, String licensePlate, String model, int horsepower, TransmissionTypeEnum transmissionType,
               FuelTypeEnum fuelType, SatNavEnum satNav, int mileage) {
        this.type = CarValidation.validateCarType(type);
        this.brand = CarValidation.validateString(brand, "Die Automarke darf nicht leer sein.");
        this.creationDate = CarValidation.validateCreatedDate(creationDate);
        this.seats = CarValidation.validateSeats(seats);
        this.basePrice = CarValidation.validateBasePrice(basePrice);
        this.currentPrice = CarValidation.validateCurrentPrice(currentPrice);
        this.licensePlate = CarValidation.validateLicensePlate(licensePlate);
        this.model = CarValidation.validateString(model, "Das Automodell darf nicht leer sein.");
        this.horsepower = CarValidation.validateHorsepower(horsepower);
        this.transmissionType = CarValidation.validateTransmissionType(transmissionType);
        this.fuelType = CarValidation.validateFuelType(fuelType);
        this.satNav = CarValidation.validateSatNav(satNav);
        this.mileage = CarValidation.validateMileage(mileage);
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

    @Override
    public int getHorsepower() {
        return horsepower;
    }

    @Override
    public TransmissionTypeEnum getTransmissionType() {
        return transmissionType;
    }

    @Override
    public FuelTypeEnum getFuelType() {
        return fuelType;
    }

    @Override
    public SatNavEnum getSatNav() {
        return satNav;
    }

    @Override
    public int getMileage() {
        return mileage;
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
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public void setTransmissionType(TransmissionTypeEnum transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Override
    public void setFuelType(FuelTypeEnum fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public void setSatNav(SatNavEnum satNav) {
        this.satNav = satNav;
    }

    @Override
    public void setMileage(int mileage) {
        this.mileage = mileage;
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
                ", horsepower=" + horsepower +
                ", transmissionType=" + transmissionType +
                ", fuelType=" + fuelType +
                ", satNav=" + satNav +
                ", mileage=" + mileage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return seats == car.seats
                && Double.compare(car.basePrice, basePrice) == 0
                && Double.compare(car.currentPrice, currentPrice) == 0
                && horsepower == car.horsepower
                && mileage == car.mileage
                && type == car.type
                && Objects.equals(brand, car.brand)
                && Objects.equals(creationDate, car.creationDate)
                && Objects.equals(licensePlate, car.licensePlate)
                && Objects.equals(model, car.model)
                && transmissionType == car.transmissionType
                && fuelType == car.fuelType
                && satNav == car.satNav;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brand, creationDate,
                seats, basePrice, currentPrice,
                licensePlate, model, horsepower,
                transmissionType, fuelType, satNav, mileage);
    }
}
