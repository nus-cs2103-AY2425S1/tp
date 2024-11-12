package seedu.address.model.tag;

/**
 * Represents the valid types of property-related tags.
 * Property tags are used to classify properties based on their type (e.g., HDB, Condo, Landed).
 */
public enum PropertyTagType {
    HDB, CONDO, RESIDENTIAL, LANDED, EC, COMMERCIAL,
    RETAIL, INDUSTRIAL, OFFICE, WAREHOUSE, SHOPHOUSE, TERRACE,
    SEMIDET, BUNGALOW, DETACHED, GCB, PENTHOUSE, MIXED,
    SERVAPT, DORM;

    /**
     * Checks if the given tag name corresponds to a valid {@code PropertyTagType}.
     * This method performs a case-insensitive check.
     *
     * @param tagName The name of the tag to validate.
     * @return {@code true} if the tag name matches any of the valid property tag types; {@code false} otherwise.
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
     * Converts a string to its corresponding {@code PropertyTagType} enum constant.
     * This method is case-insensitive.
     *
     * @param tagName The name of the tag to convert.
     * @return The corresponding {@code PropertyTagType} enum constant.
     * @throws IllegalArgumentException if the tag name does not match any valid {@code PropertyTagType}.
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
