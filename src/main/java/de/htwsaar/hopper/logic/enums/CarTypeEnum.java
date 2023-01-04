package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die unterschiedlichen Fahrzeugtypen.
 * @author Philip
 */
public enum CarTypeEnum {
    AUTO("Auto"),
    LKW("LKW"),
    MOTORRAD("Motorrad"),
    BUS("Bus"),
    LIMOUSINE("Limousine"),
    SUV("SUV"),
    VAN("Van"),
    CABRIO("Cabrio"),
    KOMBI("Kombi"),
    COUPE("Coupe"),
    SPORTWAGEN("Sportwagen"),
    ANDERE("Andere");

    private final String label;

    CarTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
