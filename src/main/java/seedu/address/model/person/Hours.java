package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tutoring hours in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHours(String)}
 */
public class Hours {

    public static final String MESSAGE_CONSTRAINTS =
            "Hours should be non-negative integers, with a maximum of 876,000.";
    public static final String VALIDATION_REGEX =
            "^(?:0*([0-9]{1,5}|[1-7][0-9]{5}|8[0-6][0-9]{4}|87[0-5][0-9]{3}|876000))$";
    public final String value;

    /**
     * Constructs a {@code Hour}.
     *
     * @param hour A valid number of hours.
     */
    public Hours(String hour) {
        requireNonNull(hour);
        checkArgument(isValidHours(hour), MESSAGE_CONSTRAINTS);
        value = clearLeadingZeroes(hour);
    }

    public int getHoursInt() {
        return Integer.parseInt(value);
    }

    /**
     * Returns true if a given string is a valid number of hours.
     */
    public static boolean isValidHours(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Removes leading zeros from the given string representation of a number.
     * If the resulting string is empty after removing the zeros, it returns "0".
     *
     * @param hours the string representation of the number to clean, must not be null
     * @return the string with leading zeros removed; if the input consists entirely of zeros,
     *         returns "0"
     */
    public static String clearLeadingZeroes(String hours) {
        requireNonNull(hours);
        String cleanedString = hours.replaceFirst("^0+", "");
        if (cleanedString.isEmpty()) {
            return "0";
        } else {
            return cleanedString;
        }
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
        if (!(other instanceof Hours)) {
            return false;
        }

        Hours otherHours = (Hours) other;
        return value.equals(otherHours.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
