package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Ward in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWard(String)}
 */
public class Ward {
    public static final String MESSAGE_CONSTRAINTS =
            "WARD must be alphanumeric and have a specific format "
                    + "(e.g., A capital letter followed by a number) - A2.\n"
                    + "It should not include special characters or spaces (e.g., 'A 1' would be invalid).\n";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9]+$";
    public final String value;

    /**
     *  Construct ward
     * @param ward
     */
    public Ward(String ward) {
        requireNonNull(ward);
        checkArgument(isValidWard(ward), MESSAGE_CONSTRAINTS);
        this.value = ward;
    }

    public static boolean isValidWard(String test) {
        return test.matches(VALIDATION_REGEX);
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
