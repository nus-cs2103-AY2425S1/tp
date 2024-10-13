package seedu.address.model.types.common;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in the format HH:mm (24-hour format) and must be valid.";

    /*
     * The time must follow the 24-hour format (HH:mm) where hours are between 00 and 23
     * and minutes are between 00 and 59.
     */
    public static final String VALIDATION_REGEX = "([01][0-9]|2[0-3]):[0-5][0-9]";

    public final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
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
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
