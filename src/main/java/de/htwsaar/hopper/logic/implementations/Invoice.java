package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.interfaces.BookingInterface;
import de.htwsaar.hopper.logic.interfaces.CarInterface;
import de.htwsaar.hopper.logic.interfaces.ChecklistInterface;
import de.htwsaar.hopper.logic.interfaces.CustomerInterface;
import de.htwsaar.hopper.logic.validations.Utils;
import de.htwsaar.hopper.repositories.CarRepository;
import de.htwsaar.hopper.repositories.ChecklistRepository;
import de.htwsaar.hopper.repositories.CustomerRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Klasse zum Erstellen von PDF-Rechnungen per Apache PDFBox.
 *
 * @author philipdausend
 */
public class Invoice {
    private static final double DEFECT_RATE = 0.5;
    private static final double LATENESS_RATE = 1.5;
    private static final double TAX_RATE = 0.19;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private BookingInterface booking;
    private CarInterface associatedCar;
    private CustomerInterface associatedCustomer;
    private ChecklistInterface associatedChecklist;
    private int linePosition;
    private double total;

    private static Invoice instance;

    /**
     * Konstruktor, der eine neue Rechnung erstellt.
     *
     * @param booking Buchung, für die die Rechnung erstellt werden soll
     */
    private Invoice(BookingInterface booking) {
        this.booking = booking;
        this.associatedCar = CarRepository.find(booking.getCarId());
        this.associatedCustomer = CustomerRepository.find(booking.getCustomerId());
        this.associatedChecklist = ChecklistRepository.find(booking.getChecklistId());
        this.linePosition = 0;
        this.total = 0.0;
    }

    /**
     * Singleton-Methode zum Generieren einer neuen Rechnung.
     * Die Methode prüft, ob bereits eine Instanz von Invoice existiert. Wenn ja, wird diese zurückgesetzt.
     * Dadurch kann eine neue Rechnung erstellt werden, ohne eine neue Instanz zu erzeugen.
     * Der gesamte Aufwand für die Erstellung einer Rechnung beschränkt sich also nur auf den Aufruf dieser Methode.
     *
     * @param booking Buchung, für die die Rechnung erstellt werden soll
     */
    public static void generate(BookingInterface booking) {
        if (instance == null) {
            instance = new Invoice(booking);
        } else {
            instance.reset(booking);
        }
        instance.checkAttributes();
        instance.generatePDF();
    }

    /**
     * Überprüft, ob die benötigten Attribute gesetzt sind.
     *
     * @throws IllegalStateException Wenn ein Attribut nicht gesetzt ist
     */
    private void checkAttributes() {
        if (booking == null) {
            throw new IllegalStateException("Buchung nicht gesetzt");
        }
        if (associatedCar == null) {
            throw new IllegalStateException("Auto nicht gesetzt");
        }
        if (associatedCustomer == null) {
            throw new IllegalStateException("Kunde nicht gesetzt");
        }
        if (associatedChecklist == null) {
            throw new IllegalStateException("Checkliste nicht gesetzt");
        }
        if (booking.getRealDropOffDate() == null) {
            throw new IllegalStateException("Rückgabe-Datum nicht gesetzt");
        }
    }

    /**
     * Setzt die Invoice-Instanz zurück.
     *
     * @param booking Buchung, für die die Rechnung erstellt werden soll
     */
    private void reset(BookingInterface booking) {
        this.booking = booking;
        this.associatedCar = CarRepository.find(booking.getCarId());
        this.associatedCustomer = CustomerRepository.find(booking.getCustomerId());
        this.associatedChecklist = ChecklistRepository.find(booking.getChecklistId());
        this.linePosition = 0;
        this.total = 0.0;
    }

    /**
     * Erstellt eine neue PDF-Rechnung.
     */
    private void generatePDF() {
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

                int billedDays = Utils.calculateDaysBetween(booking.getPickUpDate(), booking.getDropOffDate());
                writeBillingLine(
                        contentStream,
                        String.format("%s %s - Grundbetrag Miete",
                                associatedCar.getBrand(), associatedCar.getModel()),
                        associatedCar.getBasePrice());

                int daysBetweenPickUpAndRealDropOff = Utils.calculateDaysBetween(booking.getPickUpDate(), booking.getRealDropOffDate());
                if(billedDays > daysBetweenPickUpAndRealDropOff) {
                    billedDays = daysBetweenPickUpAndRealDropOff;
                    writeBillingLine(
                            contentStream,
                            String.format("%s %s - Tagespreis Miete (Anzahl Tage: %d)",
                                    associatedCar.getBrand(), associatedCar.getModel(),
                                    billedDays),
                            associatedCar.getCurrentPrice() * billedDays);
                } else {
                    writeBillingLine(
                            contentStream,
                            String.format("%s %s - Tagespreis Miete (Anzahl Tage: %d)",
                                    associatedCar.getBrand(), associatedCar.getModel(),
                                    billedDays),
                            associatedCar.getCurrentPrice() * billedDays);
                    if (!Utils.isSameDate(booking.getDropOffDate(), booking.getRealDropOffDate())) {
                        int lateDays = Utils.calculateDaysBetween(booking.getDropOffDate(), booking.getRealDropOffDate());
                        writeBillingLine(
                                contentStream,
                                String.format("%s %s - Strafzuschlag \"Überzogene Miete\" (Anzahl Tage: %d)",
                                        associatedCar.getBrand(), associatedCar.getModel(),
                                        lateDays - 1),
                                associatedCar.getCurrentPrice() * (lateDays - 1) * LATENESS_RATE);
                    }
                }

                writeFaults(contentStream);
                writeTaxAndTotal(contentStream);
                contentStream.close();

                File savedFile = new File("generated-invoice.pdf");

                doc.save(savedFile);
                Desktop.getDesktop().open(savedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (AssertionError e) {
            System.err.println("Datei nicht gefunden");
        }
    }

    /**
     * Schreibt die Adresse des Autovermieters in die PDF-Rechnung.
     *
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
     *
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeInvoiceInformation(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(340, 735);
        contentStream.showText(formatDate(Calendar.getInstance()));
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(String.valueOf(booking.getBookingId()));
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
     *
     * @param contentStream Contentstream der PDF-Rechnung
     * @param description   Beschreibung der Zeile
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     * @see #writeBillingLine(PDPageContentStream, String, double) writeBillingLine
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
     *
     * @param contentStream Contentstream der PDF-Rechnung
     * @param description   Beschreibung der Zeile
     * @param amount        Betrag der Zeile
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     * @see #writeDescriptiveLine(PDPageContentStream, String) writeDescriptiveLine
     */
    private void writeBillingLine(PDPageContentStream contentStream, String description, double amount) throws IOException {
        double rounded = Math.round(amount * 100.0) / 100.0;
        contentStream.beginText();
        contentStream.newLineAtOffset(52, calculateLinePosition());
        contentStream.showText(description);
        contentStream.newLineAtOffset(458, 0);
        contentStream.showText(df.format(amount) + "€");
        contentStream.endText();
        linePosition++;
        total += rounded;
    }

    /**
     * Schreibt Mängel in die PDF-Rechnung.
     *
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeFaults(PDPageContentStream contentStream) throws IOException {
        if (associatedChecklist.getProblemCount() > 0) {
            writeDescriptiveLine(contentStream, "Folgende Mängel wurden festgestellt:");
            if (!associatedChecklist.isClean())
                writeBillingLine(contentStream, "Fahrzeug nicht sauber", associatedCar.getBasePrice() * DEFECT_RATE);
            if (!associatedChecklist.isFueledUp())
                writeBillingLine(contentStream, "Tank nicht voll", associatedCar.getBasePrice() * DEFECT_RATE);
            if (!associatedChecklist.isUndamaged())
                writeBillingLine(contentStream, "Fahrzeug ist beschädigt", associatedCar.getBasePrice() * DEFECT_RATE);
            if (!associatedChecklist.isKeyDroppedOff())
                writeBillingLine(contentStream, "Schlüssel nicht abgegeben", associatedCar.getBasePrice() * DEFECT_RATE);
        }
    }

    /**
     * Schreibt die letzten Zeilen in die Rechnung.
     * Diese sind: Bruttobetrag, Steuersatz, berechnete Steuer und Gesamtbetrag.
     *
     * @param contentStream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeTaxAndTotal(PDPageContentStream contentStream) throws IOException {
        double tax = total * TAX_RATE;
        double beforeTax = total;
        double afterTax = beforeTax + tax;
        contentStream.beginText();
        contentStream.newLineAtOffset(510, 316);
        contentStream.showText(df.format(beforeTax) + "€");
        contentStream.newLineAtOffset(0, -26);
        contentStream.showText(df.format(TAX_RATE * 100) + "%");
        contentStream.newLineAtOffset(0, -26);
        contentStream.showText(df.format(tax) + "€");
        contentStream.newLineAtOffset(0, -50);
        contentStream.showText(df.format(afterTax) + "€");
        contentStream.endText();
    }

    /**
     * Formatiert ein Datum in das Format "dd.MM.yyyy".
     *
     * @param date Datum, das formatiert werden soll
     * @return Formatiertes Datum
     */
    private String formatDate(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date.getTime());
    }

    /**
     * Berechnet die Position der nächsten Zeile in der PDF-Rechnung.
     *
     * @return Y-Position der nächsten Zeile
     */
    private int calculateLinePosition() {
        return 572 - (linePosition * 26);
    }
}
