package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die Navi-Verfügbarkeit.
 * @author Sosthene
 */
public enum SatNavEnum {
    JA("Ja"),
    NEIN("Nein");

    private final String label;

    SatNavEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
