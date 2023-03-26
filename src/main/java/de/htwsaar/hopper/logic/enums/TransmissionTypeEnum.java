package de.htwsaar.hopper.logic.enums;

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
