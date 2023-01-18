package de.htwsaar.hopper.ui;

/**
 * Diese Klasse ermöglicht die Anzeige der benötigten Informationen in der Tabelle booking.
 * Sie fasst relevante  Attribute der Klassen Booking,Customer und Car zusammen.
 */
public class TableInformation {
    final int bookingId ;

    final String customerName ;

    final String brand ;

    public TableInformation(int bookingId , String customerName ,String brand ) {
        this.bookingId = bookingId;
        this.customerName = customerName ;
        this.brand = brand ;
    }


}
