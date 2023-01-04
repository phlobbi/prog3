package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.interfaces.BookingInterface;
import javax.persistence.*;
import java.util.Date;

/**
 * Buchungsklasse für die Datenbankverwaltung
 * @author Sosthene
 */
@Entity
public class Booking implements BookingInterface {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BookingID", unique = true)
    private int bookingId;
    
    @Basic
    @Column(name = "CarID")
    private int carId;
    
    @Basic
    @Column(name = "CustomerID")
    private int customerId;
    
    @Basic
    @Column(name = "PickUpDate")
    @Temporal(TemporalType.DATE)
    private Date pickUpDate;
    
    @Basic
    @Column(name = "DropOffDate")
    @Temporal(TemporalType.DATE)
    private Date dropOffDate;
    
    @Basic
    @Column(name = "RealDropOffDate")
    @Temporal(TemporalType.DATE)
    private Date realDropOffDate;

    /**
    * Standard-Konstruktor
    */
    public Booking() {
    }
    
    /**
     * Konstruktor zum Anlegen von Buchungen
     * @param bookingId BuchungID(Schuessel-Attribut)
     * @param carId ID eines beliebigen Autos
     * @param customerId ID eines Kunden
     * @param pickUpDate Abholdatum eines Autos vom Kunde
     * @param dropOffDate Rueckgabedatum eines Autos vom Kunde
     * @param realDropOffDate Tatsaechliches Rueckgabedatum eines Autos vom Kunden
     */
    public Booking(int bookingId, int carId, int customerId, Date pickUpDate, Date dropOffDate, Date realDropOffDate) {
        this.bookingId = bookingId;
        this.carId = carId;
        this.customerId = customerId;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.realDropOffDate = realDropOffDate;
    }

    /* GETTER */
    public int getBookingId() {
        return bookingId;
    }

    public int getCarId() {
        return carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public Date getRealDropOffDate() {
        return realDropOffDate;
    }

    /* SETTER */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public void setRealDropOffDate(Date realDropOffDate) {
        this.realDropOffDate = realDropOffDate;
    }
}
