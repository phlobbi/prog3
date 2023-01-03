package DatabaseClasses;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Bookings {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BookingID", unique = true)
    private int bookingId;
    @Basic
    @Column(name = "CarID", unique = true)
    private int carId;
    @Basic
    @Column(name = "CustomerID")
    private int customerId;
    @Basic
    @Column(name = "PickUpDate", unique = true)
    private Date pickUpDate;
    @Basic
    @Column(name = "DropOffDate")
    private Date dropOffDate;
    @Basic
    @Column(name = "RealDropOffDate")
    private Date realDropOffDate;

    //GETTERS

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

    //SETTERS


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

    @Override
    /**
     * Equals-Methode zur Überprüfung von Objektgleichheit.
     */
    public boolean equals(Object object) {
        if (this == object){
            return true;
        } else if (object == null || getClass() != object.getClass()){
            return false;
        }

        Bookings bookings = (Bookings) object;

        if (bookingId != bookings.bookingId){
            return false;
        }else if (carId != bookings.carId){
            return false;
        }else if (customerId != bookings.customerId){
            return false;
        }
        if (!Objects.equals(pickUpDate, bookings.pickUpDate)) {
            return false;
        }else if (!Objects.equals(dropOffDate, bookings.dropOffDate)) {
            return false;
        }else if (!Objects.equals(realDropOffDate, bookings.realDropOffDate)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookingId;
        result = 31 * result + carId;
        result = 31 * result + customerId;
        result = 31 * result + (pickUpDate != null ? pickUpDate.hashCode() : 0);
        result = 31 * result + (dropOffDate != null ? dropOffDate.hashCode() : 0);
        result = 31 * result + (realDropOffDate != null ? realDropOffDate.hashCode() : 0);
        return result;
    }
}
