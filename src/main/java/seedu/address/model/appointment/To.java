package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

public class To {
    public final String value;

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
