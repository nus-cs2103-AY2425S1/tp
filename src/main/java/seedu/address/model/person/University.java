package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's university in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUniversity(String)}
 */
public class University {

    public static final String MESSAGE_CONSTRAINTS =
            "University names should not be blank and should only contain alphanumeric characters and spaces.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} \\-]*";
    public final String value;

    /**
     * Constructs a {@code University}.
     *
     * @param university A valid university name.
     */
    public University(String university) {
        requireNonNull(university);
        checkArgument(isValidUniversity(university), MESSAGE_CONSTRAINTS);
        value = university;
    }

    /**
     * Returns true if a given string is a valid university name.
     */
    public static boolean isValidUniversity(String test) {
        return test.trim().matches(VALIDATION_REGEX);
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

        if (!(other instanceof University)) {
            return false;
        }

        University otherUniversity = (University) other;
        return value.equals(otherUniversity.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
