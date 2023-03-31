package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die unterschiedlichen Getriebetypen.
 * @author Sosthene
 */
public enum TransmissionTypeEnum {
    MANUELL("Manuell"),
    AUTOMATIK("Automatik");

    private final String label;

    TransmissionTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
