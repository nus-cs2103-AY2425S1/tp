package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Ward in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWard(String)}
 */
public class Ward {
    public static final String MESSAGE_CONSTRAINTS =
            "WARD field cannot be an empty text";
    public static final String VALIDATION_REGEX = ".+";
    public static final String WARNING_SPECIAL_CHARACTER = "Warning! Ward field includes special characters.\n";
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

    /**
     * Returns true if the input contains at least one non-alphanumeric character.
     */
    public boolean containsSpecialChar() {
        // Regular expression to match any non-alphanumeric character
        return value.matches(".*[^a-zA-Z0-9].*");
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
