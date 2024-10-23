package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a time in the contacts book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Times must be in the format of HHmm";

    /*
    * Matches an input string to the Hhmm format.
    */
    public static final String VALIDATION_REGEX = "^(?:[01]\\d|2[0-3])[0-5]\\d$";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    public final LocalTime time;

    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param input A valid time.
     */
    public Time(String input) {
        requireNonNull(input);
        checkArgument(isValidTime(input), MESSAGE_CONSTRAINTS);

        time = LocalTime.parse(input, FORMATTER);
        value = input;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return time.format(FORMATTER);
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
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}
