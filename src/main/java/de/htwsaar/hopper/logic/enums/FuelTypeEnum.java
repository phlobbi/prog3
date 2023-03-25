package de.htwsaar.hopper.logic.enums;

public enum FuelTypeEnum {
    DIESEL("Diesel"),
    BENZIN("Benzin"),
    ELECTRIC("electric")
    ;
    private final String label;
    FuelTypeEnum(String label) {
        this.label = label;
    }
}
