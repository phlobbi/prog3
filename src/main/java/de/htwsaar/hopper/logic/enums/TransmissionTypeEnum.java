package de.htwsaar.hopper.logic.enums;

public enum TransmissionTypeEnum {
    MANUAL("Manual"),
    AUTOMATIC("Automatic")
    ;
    private final String label;

    TransmissionTypeEnum(String label) {
        this.label = label;
    }
}
