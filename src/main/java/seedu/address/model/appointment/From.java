package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

public class From {
    public final String value;

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
