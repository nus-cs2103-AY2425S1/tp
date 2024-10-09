package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's Ward in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWard(String)}
 */
public class Ward {
    public final String value;
    public Ward(String ward) {
        requireNonNull(ward);
//        checkArgument(isValidWard(ward), MESSAGE_CONSTRAINTS);
        this.value = ward;
    }

    public static boolean isValidWard(String test) {
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
        if (!(other instanceof Ward)) {
            return false;
        }

        Ward otherWard = (Ward) other;
        return value.equals(otherWard.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
