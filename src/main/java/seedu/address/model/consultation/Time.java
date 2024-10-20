package seedu.address.model.consultation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Time in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Times should be in the format HH:MM, "
        + "where hour is between 00 and 23, and minute between 00 and 59 (e.g., 14:30).";

    private final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time string.
     * @throws IllegalArgumentException if the given {@code time} is not a valid time.
     */
    public Time(String time) {
        if (!isValidTime(time)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns the value of the time as a string.
     *
     * @return The string representation of the time.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid time format (HH:MM) and represents a real time.
     *
     * @param test The string to test for validity.
     * @return True if the string represents a valid time, false otherwise.
     */
    public static boolean isValidTime(String test) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime.parse(test, timeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
