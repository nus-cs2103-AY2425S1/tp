package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)}
 */
public class Sex {

    public static final String MESSAGE_CONSTRAINTS =
            "Sex should either be M or F";

    public final String value;

    /**
     * Constructs an {@code Sex}.
     *
     * @param sex A valid sex.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        value = sex;
    }

    public static boolean isValidSex(String test) {
        return true;
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
        if (!(other instanceof Sex)) {
            return false;
        }

        Sex otherSex = (Sex) other;
        return value.equals(otherSex.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
