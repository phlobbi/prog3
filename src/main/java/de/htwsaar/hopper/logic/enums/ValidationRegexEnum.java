package de.htwsaar.hopper.logic.enums;

/**
 * Enum für die regulären Ausdrücke, die zur Validierung von Eingaben verwendet werden.
 * @author Bennet
 */
public enum ValidationRegexEnum {

    PHONE_NUMBER("^(\\+49|0)[0-9]{1,5}(\\/| )?[0-9]{4,10}"),

    DRIVER_LICENSE_NUMBER("^[A-z0-9][0-9]{2}[A-z0-9]{6}[0-9]{1}[A-z0-9]{1}"),

    LICENSE_PLATE("^[A-ZÖÜÄ]{1,3}-[A-ZÖÜÄ]{1,2}-[1-9]{1}[0-9]{1,3}"),

    HOUSE_NUMBER("^[0-9]{1,3}[A-z]{0,1}"),

    GERMAN_ZIP_CODE("^(0[1-9]\\d{3}|[1-9]\\d{4})$");

    private final String regex;

    ValidationRegexEnum(String label) {
        this.regex = label;
    }

    public String getRegex() {
        return regex;
    }
}
