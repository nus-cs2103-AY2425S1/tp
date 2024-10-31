package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Year in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class Year {
    public static final String MESSAGE_CONSTRAINTS = "Year should be a positive number";
    public static final String VALIDATION_REGEX = "[1-9]\\d*";
    private static final Year EMPTY_YEAR = new Year();

    public final String value;

    /**
     * Constructs a {@code Year}.
     *
     * @param year A valid year.
     */
    private Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        value = year;
    }

    /**
     * Constructs an empty {@code Year}.
     *
     */
    private Year() {
        value = "";
    }

    /**
     * Constructs a {@code Year} or an empty {@code Year} instance.
     *
     * @param year A valid year or an empty string
     */
    public static Year makeYear(String year) {
        requireNonNull(year);

        if (year.isEmpty()) {
            return EMPTY_YEAR;
        }

        return new Year(year);
    }

    /**
     * Returns true if a given string is a valid Year.
     */
    public static boolean isValidYear(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Year)) {
            return false;
        }

        Year otherYear = (Year) other;
        return value.equals(otherYear.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
