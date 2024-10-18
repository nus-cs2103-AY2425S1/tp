package seedu.address.model.tag;

public enum PersonTagType {
    BUYER, SELLER, LANDLORD, TENANT;

    /**
     * Checks if the given tag name is a valid PersonTag.
     * This method is case-insensitive.
     */
    public static boolean isValidPersonTag(String tagName) {
        for (PersonTagType type : PersonTagType.values()) {
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
    public static PersonTagType fromString(String tagName) {
        for (PersonTagType type : PersonTagType.values()) {
            if (type.name().equalsIgnoreCase(tagName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PersonTag: " + tagName);
    }
}

