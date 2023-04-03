package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die unterschiedlichen Kraftstofftypen.
 *
 * @author Sosthene
 */
public enum FuelTypeEnum {
    DIESEL("Diesel"),
    BENZIN("Benzin"),
    ELEKTRO("Elektro");

    private final String label;

    FuelTypeEnum(String label) {
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
