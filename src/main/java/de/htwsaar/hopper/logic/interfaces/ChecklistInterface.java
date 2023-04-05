package de.htwsaar.hopper.logic.interfaces;

/**
 * Interface für die Klasse Checklist.
 * @author Philip
 */
@SuppressWarnings("MissingJavadoc")
public interface ChecklistInterface extends DBObjectInterface {
    int getProblemCount();

    /* GETTER */
    int getChecklistId();
    boolean isFueledUp();
    boolean isUndamaged();
    boolean isClean();
    boolean isKeyDroppedOff();

    /* SETTER */
    void setFueledUp(boolean fueledUp);
    void setUndamaged(boolean undamaged);
    void setClean(boolean clean);
    void setKeyDroppedOff(boolean keyDroppedOff);

    /* toString */
    String toString();

    /* addToBooking */
    void addToBooking(int bookingId);
}
