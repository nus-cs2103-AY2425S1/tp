package seedu.address.model.listing;

/**
 * Represents the size of a listing in square meters in the real estate application.
 * The size is stored as an integer value representing the area in square meters (m²).
 */
public class Area {
    public static final String MESSAGE_CONSTRAINTS =
            "Area should only contain positive numbers and cannot start with zeroes, "
                    + "and it should be at least 2 digits long";
    private static final String VALIDATION_REGEX = "^[1-9]\\d{1,}$";

    private final String squareMeters;

    /**
     * Constructs a {@code Area}.
     *
     * @param squareMeters The size of the listing in square meters.
     */
    public Area(String squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getArea() {
        return this.squareMeters;
    }

    /**
     * Returns true if a given string is a valid area.
     */
    public static boolean isValidArea(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%s", this.squareMeters);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Area)) {
            return false;
        }

        Area otherSize = (Area) other;
        return squareMeters.equals(otherSize.squareMeters);
    }

}
