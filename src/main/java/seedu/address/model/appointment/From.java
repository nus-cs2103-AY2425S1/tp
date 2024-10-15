package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents the start time of an appointment in the address book.
 * The {@code From} class encapsulates the start time as a string.
 * This value cannot be null.
 */
public class From {
    public final String value;

    /**
     * Constructs a {@code From} object with the specified start time value.
     * The value must not be {@code null}.
     *
     * @param value A string representing the start time.
     */
    public From(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof From
                && value.equals(((From) other).value));
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
