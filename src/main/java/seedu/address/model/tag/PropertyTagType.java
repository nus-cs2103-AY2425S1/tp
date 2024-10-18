package seedu.address.model.tag;

public enum PropertyTagType {
    HDB, CONDO, LANDED;

    /**
     * Checks if the given tag name is a valid PropertyTag.
     * This method is case-insensitive.
     */
    public static boolean isValidPropertyTag(String tagName) {
        for (PropertyTagType type : PropertyTagType.values()) {
            if (type.name().equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the enum constant matching the given tag name.
     * This method is case-insensitive.
     */
    public static PropertyTagType fromString(String tagName) {
        for (PropertyTagType type : PropertyTagType.values()) {
            if (type.name().equalsIgnoreCase(tagName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PropertyTag: " + tagName);
    }
}
