package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die unterschiedlichen Getriebetypen.
 *
 * @author Sosthene
 */
@SuppressWarnings("MissingJavadoc")
public enum TransmissionTypeEnum {
    MANUELL("Manuell"),
    AUTOMATIK("Automatik");

    private final String label;

    TransmissionTypeEnum(String label) {
        this.label = label;
    }

    /**
     * Gibt das Label des Enums zurück.
     *
     * @return Label des Enums
     */
    public String getLabel() {
        return label;
    }
}
