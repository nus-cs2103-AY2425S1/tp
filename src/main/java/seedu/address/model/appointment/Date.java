package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents the date of an appointment in the address book.
 * The {@code Date} class encapsulates the appointment date as a string.
 * This value cannot be null.
 */
public class Date {
    public final String value;

    /**
     * Constructs a {@code Date} object with the specified date value.
     * The value must not be {@code null}.
     *
     * @param value A string representing the date.
     */
    public Date(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date
                && value.equals(((Date) other).value));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
