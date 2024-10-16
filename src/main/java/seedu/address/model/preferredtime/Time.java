package seedu.address.model.preferredtime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the TIme of PreferredTime to play games in the gamer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in range from 0000 to 2359.";
    public static final String VALIDATION_REGEX = "^([01][0-9]|2[0-3])[0-5][0-9]$";

    public final String start;

    /**
     * Constructs a {@code Time}.
     */
    public Time(String start) {
        requireNonNull(start);
        checkArgument(isValidTime(start), MESSAGE_CONSTRAINTS);
        this.start = start;
    }

    /**
     * Returns true if a given string is a valid Time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return start.equals(otherTime.start);
    }

    @Override
    public int hashCode() {
        return start.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return start;
    }
}
