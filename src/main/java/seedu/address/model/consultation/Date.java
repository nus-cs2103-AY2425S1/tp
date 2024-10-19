package seedu.address.model.consultation;

import java.util.Objects;

/**
 * Represents a Date in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    
    public static final String MESSAGE_CONSTRAINTS = "Dates should be in the format YYYY-MM-DD";
    
    private final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date string.
     */
    public Date(String date) {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = date;
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid date format (YYYY-MM-DD).
     */
    public static boolean isValidDate(String test) {
        // A simple regex to check the date format. Can be extended for more rigorous checks.
        return test.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
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