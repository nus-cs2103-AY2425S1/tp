package seedu.address.model.consultation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Time in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Times should be in the format HH:MM, "
            + "where hour is between 00 and 23, and minute between 00 and 59.";

    private final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time string.
     */
    public Time(String time) {
        if (!isValidTime(time)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = time;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid time format (HH:MM) and represents a real time.
     */
    public static boolean isValidTime(String test) {
        // Define the time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            // Parse the time and ensure it's valid
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
