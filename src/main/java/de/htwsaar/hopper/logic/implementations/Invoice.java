package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.enums.CarTypeEnum;
import de.htwsaar.hopper.logic.interfaces.BookingInterface;
import de.htwsaar.hopper.logic.interfaces.CarInterface;
import de.htwsaar.hopper.logic.interfaces.ChecklistInterface;
import de.htwsaar.hopper.logic.interfaces.CustomerInterface;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Klasse zum Erstellen von PDF-Rechnungen per Apache PDFBox.
 *
 * @author philipdausend
 */
public class Invoice {
    private static final double MAENGELSATZ = 0.5;
    private static final double VERSPAETUNGSZUSCHLAG = 0.5;
    private static final double STEUERSATZ = 0.19;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final BookingInterface booking;
    private final CarInterface associatedCar;
    private final CustomerInterface associatedCustomer;
    private final ChecklistInterface associatedChecklist;
    private int linePosition;
    private double total;

    /**
     * Konstruktor, der eine neue Rechnung erstellt.
     * @param booking Buchung, für die die Rechnung erstellt werden soll
     */
    public Invoice(BookingInterface booking) {
        Calendar expirationDate = Calendar.getInstance();
        Calendar productionDate = Calendar.getInstance();
        Calendar pickUpDay = Calendar.getInstance();
        Calendar dropOffDay = Calendar.getInstance();
        Calendar realDropOffDay = Calendar.getInstance();
        expirationDate.add(Calendar.YEAR, 1);
        productionDate.add(Calendar.YEAR, -2);
        pickUpDay.set(2023, Calendar.JANUARY, 1);
        dropOffDay.set(2023, Calendar.JANUARY, 3);
        realDropOffDay.set(2023, Calendar.JANUARY, 5);
        this.booking = new Booking(5, 5, pickUpDay, dropOffDay);
        this.booking.setRealDropOffDate(realDropOffDay);
        this.associatedCar = new Car(CarTypeEnum.AUTO, "Audi", productionDate, 5, 100, 20, "SB-AB-23", "A4");
        this.associatedCustomer = new Customer("Max", "Mustermann", "max@muster.de", "Musterstraße", "1", "66130", "Saarbrücken", "0176/12345678", "DE06500105177825353352", "L01C0097Z31", expirationDate);
        this.associatedChecklist = new Checklist(false, false, false, false);
        this.linePosition = 0;
        this.total = 0.0;
    }

    /**
     * Erstellt eine neue PDF-Rechnung.
     */
    public void generatePDF() {
        URL url = Invoice.class.getResource("invoice-template.pdf");
        File file;
        try {
            assert url != null;
            file = new File(url.getPath());
            try (PDDocument doc = PDDocument.load(file)) {
                PDPage page = doc.getPage(0);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);

                contentStream.setFont(PDType1Font.HELVETICA, 10);
                writeStoreAddress(contentStream);
                writeInvoiceInformation(contentStream);
                int bookedDays = calculateDaysBetween(booking.getPickUpDate(), booking.getDropOffDate());
                writeBillingLine(
                        contentStream,
                        String.format("%s %s - Grundbetrag Miete",
                                associatedCar.getBrand(), associatedCar.getModel()),
                        associatedCar.getBasePrice());
                writeBillingLine(
                        contentStream,
                        String.format("%s %s - Tagespreis Miete (Anzahl Tage: %d)",
                                associatedCar.getBrand(), associatedCar.getModel(),
                                bookedDays),
                associatedCar.getCurrentPrice() * bookedDays);
                if(!isSameDate(booking.getDropOffDate(), booking.getRealDropOffDate())) {
                    int lateDays = calculateDaysBetween(booking.getDropOffDate(), booking.getRealDropOffDate());
                    writeBillingLine(
                            contentStream,
                            String.format("%s %s - Strafzuschlag \"Überzogene Miete\" (Anzahl Tage: %d)",
                                    associatedCar.getBrand(), associatedCar.getModel(),
                                    lateDays - 1),
                            associatedCar.getCurrentPrice() * (lateDays - 1) * VERSPAETUNGSZUSCHLAG);
                }
                writeFaults(contentStream);
                writeTaxAndTotal(contentStream);
                contentStream.close();
                doc.save(new File("generated-invoice.pdf"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (AssertionError e) {
            System.err.println("Datei nicht gefunden");
        }
    }

    /**
     * Schreibt die Adresse des Autovermieters in die PDF-Rechnung.
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeStoreAddress(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(51, 710);
        contentStream.showText("Hopper's Autovermietung");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Hauptstraße 1");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("12345 Hopperstadt");
        contentStream.endText();
    }

    /**
     * Schreibt die Rechnungsdaten in die PDF-Rechnung.
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeInvoiceInformation(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(340, 735);
        contentStream.showText(formatDate(Calendar.getInstance()));
        contentStream.newLineAtOffset(0, -12);
        // TODO Zeile entkommentieren, sobald das Testen abgeschlossen ist
        // contentstream.showText(String.valueOf(booking.getBookingId()));
        contentStream.showText("123");
        contentStream.newLineAtOffset(0, -38);
        contentStream.showText(associatedCustomer.getFirstName() + " " + associatedCustomer.getLastName());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(associatedCustomer.getStreet() + " " + associatedCustomer.getHouseNumber());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(associatedCustomer.getZipCode() + " " + associatedCustomer.getCity());
        contentStream.endText();
    }

    /**
     * Schreibt eine Zeile in die PDF-Rechnung, ohne einen Betrag.
     * @see #writeBillingLine(PDPageContentStream, String, double) writeBillingLine
     * @param contentStream Contentstream der PDF-Rechnung
     * @param description Beschreibung der Zeile
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeDescriptiveLine(PDPageContentStream contentStream, String description) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(52, calculateLinePosition());
        contentStream.showText(description);
        contentStream.endText();
        linePosition++;
    }

    /**
     * Schreibt eine Zeile mit Betrag in die PDF-Rechnung.
     * Der Betrag wird auf zwei Nachkommastellen gerundet und zum Gesamtbetrag addiert.
     * @see #writeDescriptiveLine(PDPageContentStream, String) writeDescriptiveLine
     * @param contentStream Contentstream der PDF-Rechnung
     * @param description Beschreibung der Zeile
     * @param amount Betrag der Zeile
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeBillingLine(PDPageContentStream contentStream, String description, double amount) throws IOException {
        double rounded = Math.round(amount * 100.0) / 100.0;
        contentStream.beginText();
        contentStream.newLineAtOffset(52, calculateLinePosition());
        contentStream.showText(description);
        contentStream.newLineAtOffset(467, 0);
        contentStream.showText(df.format(amount) + "€");
        contentStream.endText();
        linePosition++;
        total += rounded;
    }

    /**
     * Schreibt Mängel in die PDF-Rechnung.
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeFaults(PDPageContentStream contentStream) throws IOException {
        if(associatedChecklist.getProblemCount() > 0) {
            writeDescriptiveLine(contentStream, "Folgende Mängel wurden festgestellt:");
            if (!associatedChecklist.isClean()) writeBillingLine(contentStream, "Fahrzeug nicht sauber", associatedCar.getBasePrice() * MAENGELSATZ);
            if (!associatedChecklist.isFueledUp()) writeBillingLine(contentStream, "Tank nicht voll", associatedCar.getBasePrice() * MAENGELSATZ);
            if (!associatedChecklist.isUndamaged()) writeBillingLine(contentStream, "Fahrzeug ist beschädigt", associatedCar.getBasePrice() * MAENGELSATZ);
            if (!associatedChecklist.isKeyDroppedOff()) writeBillingLine(contentStream, "Schlüssel nicht abgegeben", associatedCar.getBasePrice() * MAENGELSATZ);
        }
    }

    /**
     * Schreibt die letzten Zeilen in die Rechnung.
     * Diese sind: Bruttobetrag, Steuersatz, berechnete Steuer und Gesamtbetrag.
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeTaxAndTotal(PDPageContentStream contentStream) throws IOException {
        double tax = total * STEUERSATZ;
        double beforeTax = total - tax;
        contentStream.beginText();
        contentStream.newLineAtOffset(510, 316);
        contentStream.showText(df.format(beforeTax) + "€");
        contentStream.newLineAtOffset(0, -26);
        contentStream.showText(df.format(STEUERSATZ * 100) + "%");
        contentStream.newLineAtOffset(0, -26);
        contentStream.showText(df.format(tax) + "€");
        contentStream.newLineAtOffset(0, -50);
        contentStream.showText(df.format(total) + "€");
        contentStream.endText();
    }

    /**
     * Formatiert ein Datum in das Format "dd.MM.yyyy".
     * @param date Datum, das formatiert werden soll
     * @return Formatiertes Datum
     */
    private String formatDate(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date.getTime());
    }

    /**
     * Berechnet die Position der nächsten Zeile in der PDF-Rechnung.
     * @return Y-Position der nächsten Zeile
     */
    private int calculateLinePosition() {
        return 572 - (linePosition * 26);
    }

    /**
     * Berechnet die Anzahl der Tage zwischen zwei Kalenderdaten.
     * Die Methode berücksichtigt dabei, dass das Enddatum inklusive ist.
     * Beispiel: 01.01.2020 - 03.01.2020 = 3 Tage
     * @param start Startdatum
     * @param end Enddatum
     * @return Anzahl der Tage zwischen den beiden Kalenderdaten
     */
    private int calculateDaysBetween(Calendar start, Calendar end) {
        clearHourMinuteSecond(start);
        clearHourMinuteSecond(end);
        return (int) (ChronoUnit.DAYS.between(start.toInstant(), end.toInstant()) + 1);
    }

    /**
     * Setzt die Stunden, Minuten und Sekunden eines Kalenderdatums auf 0.
     * @param date Kalenderdatum, das bereinigt werden soll
     */
    private void clearHourMinuteSecond(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Prüft, ob zwei Calendar-Objekte das gleiche Datum darstellen.
     * @param date1 Kalenderdatum 1
     * @param date2 Kalenderdatum 2
     * @return true, wenn die beiden Kalenderdaten das gleiche Datum darstellen, sonst false
     */
    private boolean isSameDate(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.DAY_OF_YEAR) == date2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Main-Methode zum Testen der Klasse.
     * @param args Kommandozeilenargumente
     * @deprecated Wird vorerst zum Testen der Klasse verwendet und danach entfernt. Nicht für Produktivsysteme verwenden.
     */
    public static void main(String[] args) {
        Invoice invoice = new Invoice(null);
        invoice.generatePDF();
    }
}
