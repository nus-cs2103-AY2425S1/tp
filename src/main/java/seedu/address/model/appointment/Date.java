package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

public class Date {
    public final String value;

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
