package de.htwsaar.hopper.logic.enums;

public enum FuelTypeEnum {
    DIESEL("Diesel"),
    BENZIN("Benzin"),
    ELEKTRO("Elektro");

    private final String label;

    FuelTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
