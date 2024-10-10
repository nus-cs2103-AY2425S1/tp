package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents additional details of a person, including a specific time format.
 * Guarantees: immutable; is always valid
 */
public class Detail {

    public final String value;

    /**
     * Constructs a {@code Detail}.
     *
     * @param detail A valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        this.value = detail;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Detail)) {
            return false;
        }

        Detail otherDetail = (Detail) other;
        return value.equals(otherDetail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
