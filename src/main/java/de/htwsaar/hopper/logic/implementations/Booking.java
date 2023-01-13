package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.interfaces.BookingInterface;
import javax.persistence.*;
import java.util.Date;

/**
 * Buchungsklasse für die Datenbankverwaltung
 * @author Sosthene
 */
@Entity
@Table(name = "Bookings")
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
     * Konstruktor zum Anlegen von Buchungen, ohne realDropOffDate
     * @param bookingId ID der Buchung
     * @param carId ID des gebuchten Autos
     * @param customerId ID des Kunden, der das Auto gebucht hat
     * @param pickUpDate Abholdatum eines Autos vom Kunde
     * @param dropOffDate Geplantes Rückgabedatum eines Autos vom Kunde
     */
    public Booking(int bookingId, int carId, int customerId, Date pickUpDate, Date dropOffDate) {
        this.bookingId = bookingId;
        this.carId = carId;
        this.customerId = customerId;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
    }

    /* GETTER */
    @Override
    public int getBookingId() {
        return bookingId;
    }

    @Override
    public int getCarId() {
        return carId;
    }

    @Override
    public int getCustomerId() {
        return customerId;
    }

    @Override
    public Date getPickUpDate() {
        return pickUpDate;
    }

    @Override
    public Date getDropOffDate() {
        return dropOffDate;
    }

    @Override
    public Date getRealDropOffDate() {
        return realDropOffDate;
    }

    /* SETTER */
    @Override
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    @Override
    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    @Override
    public void setRealDropOffDate(Date realDropOffDate) {
        this.realDropOffDate = realDropOffDate;
    }
}
