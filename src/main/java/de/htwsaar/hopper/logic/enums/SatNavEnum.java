package de.htwsaar.hopper.logic.enums;

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
