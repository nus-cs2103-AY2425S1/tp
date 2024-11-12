package seedu.address.model.tag;

/**
 * Represents the valid types of person-related tags.
 * Person tags are used to classify individuals based on their role (e.g., Buyer, Seller, Landlord, Tenant, etc).
 */
public enum PersonTagType {
    BUYER, SELLER, LANDLORD, TENANT, DEVELOPER, INVESTOR, MANAGER, CONTRACTOR;

    /**
     * Checks if the given tag name corresponds to a valid {@code PersonTagType}.
     * This method performs a case-insensitive check.
     *
     * @param tagName The name of the tag to validate.
     * @return {@code true} if the tag name matches any of the valid person tag types; {@code false} otherwise.
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
     * Converts a string to its corresponding {@code PersonTagType} enum constant.
     * This method is case-insensitive.
     *
     * @param tagName The name of the tag to convert.
     * @return The corresponding {@code PersonTagType} enum constant.
     * @throws IllegalArgumentException if the tag name does not match any valid {@code PersonTagType}.
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
