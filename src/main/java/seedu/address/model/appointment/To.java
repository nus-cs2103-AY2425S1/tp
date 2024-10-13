package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Represents the end time of an appointment in the address book.
 * The {@code To} class encapsulates the end time as a string.
 * This value cannot be null.
 */
public class To {
    public final String value;

    /**
     * Constructs a {@code To} object with the specified end time value.
     * The value must not be {@code null}.
     *
     * @param value A string representing the end time.
     */
    public To(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof To
                && value.equals(((To) other).value));
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
