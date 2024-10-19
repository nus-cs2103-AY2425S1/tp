package seedu.address.model.consultation;

import java.util.Objects;

/**
 * Represents a Time in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Times should be in the format HH:MM";
    
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
     * Returns true if a given string is a valid time format (HH:MM).
     */
    public static boolean isValidTime(String test) {
        // A simple regex to check time format. Can be extended for more rigorous checks.
        return test.matches("\\d{2}:\\d{2}");
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
