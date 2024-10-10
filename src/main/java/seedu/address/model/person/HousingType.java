package seedu.address.model.person;

/**
 * Represents the type of housing that a person resides in.
 */
public enum HousingType {
    CONDO, HDB, APARTMENT, BTO, OTHERS, INVALID_HOUSING_TYPE;

    /**
     * Checks if the given housing type is valid.
     *
     * @param housingType The housing type to be checked.
     * @return True if the housing type is valid, false otherwise.
     */
    public static boolean isValidHousingType(String housingType) {
        return housingType.equals("a") || housingType.equals("c") || housingType.equals("b")
                || housingType.equals("h") || housingType.equals("o");
    }

    /**
     * Returns the HousingType enum corresponding to the given housing type.
     *
     * @param housingType The housing type to be converted.
     * @return The HousingType enum corresponding to the given housing type.
     */
    public static HousingType getHousingType(String housingType) {
        return switch (housingType) {
        case "a" -> APARTMENT;
        case "c" -> CONDO;
        case "b" -> BTO;
        case "h" -> HDB;
        case "o" -> OTHERS;
        default -> INVALID_HOUSING_TYPE;
        };
    }
}
