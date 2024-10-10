package seedu.address.model.person;

public enum HousingType {
    CONDO, HDB, APARTMENT, BTO, OTHERS, INVALID_HOUSING_TYPE;

    public static boolean isValidHousingType(String housingType) {
        return housingType.equals("a") || housingType.equals("c") || housingType.equals("b")
                || housingType.equals("h") || housingType.equals("o");
    }

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
