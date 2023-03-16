package de.htwsaar.hopper.logic.implementations;

import de.htwsaar.hopper.logic.interfaces.BookingInterface;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Klasse zum Erstellen von PDF-Rechnungen per Apache PDFBox.
 *
 * @author philipdausend
 */
public class Invoice {
    private final BookingInterface booking;

    /**
     * Konstruktor, der eine neue Rechnung erstellt.
     * @param booking Buchung, für die die Rechnung erstellt werden soll
     */
    public Invoice(BookingInterface booking) {
        this.booking = new Booking(5, 5, Calendar.getInstance(), Calendar.getInstance());
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
     * @param contentstream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeStoreAddress(PDPageContentStream contentstream) throws IOException {
        contentstream.beginText();
        contentstream.newLineAtOffset(51, 710);
        contentstream.showText("Hopper's Autovermietung");
        contentstream.newLineAtOffset(0, -12);
        contentstream.showText("Hauptstraße 1");
        contentstream.newLineAtOffset(0, -12);
        contentstream.showText("12345 Hopperstadt");
        contentstream.endText();
    }

    /**
     * Schreibt die Rechnungsdaten in die PDF-Rechnung.
     * @param contentstream Contentstream der PDF-Rechnung
     * @throws IOException Falls es beim Schreiben zu einem Fehler kommt
     */
    private void writeInvoiceInformation(PDPageContentStream contentstream) throws IOException {
        contentstream.beginText();
        contentstream.newLineAtOffset(340, 735);
        contentstream.showText(formatDate(Calendar.getInstance()));
        contentstream.newLineAtOffset(0, -12);
        // TODO Zeile entkommentieren, sobald das Testen abgeschlossen ist
        // contentstream.showText(String.valueOf(booking.getBookingId()));
        contentstream.showText("123");
        contentstream.newLineAtOffset(0, -38);
        contentstream.showText("Max Mustermann");
        contentstream.newLineAtOffset(0, -12);
        contentstream.showText("Musterstraße 1");
        contentstream.newLineAtOffset(0, -12);
        contentstream.showText("12345 Musterstadt");
        contentstream.endText();
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
     * Main-Methode zum Testen der Klasse.
     * @param args Kommandozeilenargumente
     * @deprecated Wird vorerst zum Testen der Klasse verwendet und danach entfernt. Nicht für Produktivsysteme verwenden.
     */
    public static void main(String[] args) {
        Invoice invoice = new Invoice(null);
        invoice.generatePDF();
    }
}
