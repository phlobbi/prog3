package de.htwsaar.hopper.ui;

/**
 * Diese Klasse ermöglicht die Anzeige der benötigten Informationen in der Tabelle booking.
 * Sie fasst relevante  Attribute der Klassen Booking,Customer und Car zusammen.
 */
public class BookingTableInformation {
    private final int bookingId ;

    private  final String customerName ;

    private final String brand ;

    public BookingTableInformation(int bookingId , String customerName , String brand ) {
        this.bookingId = bookingId;
        this.customerName = customerName ;
        this.brand = brand ;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getBrand() {
        return brand;
    }

    public int getBookingId() {
        return bookingId;
    }

}
