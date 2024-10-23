package seedu.address.model.property;

/**
 * Represents allowed Property types in the address book.
 */
public enum PropertyType {
    HDB, CONDO, LANDED;

    /**
     * Checks whether input string (case-insensetive) matches allowed property type
     */
    public static boolean isValidEnumValue(String input) {
        for (PropertyType type : PropertyType.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
