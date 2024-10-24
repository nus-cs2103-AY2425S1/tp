package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Education in the address book.
 * Guarantees: immutable; level is valid as declared in {@link #isValidEducationLevel(String)}
 */
public class Education {
    public static final String MESSAGE_CONSTRAINTS = "Education level should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String educationLevel;

    /**
     * Constructs an {@code Education}.
     *
     * @param educationLevel A valid education level.
     */
    public Education(String educationLevel) {
        requireNonNull(educationLevel);
        checkArgument(isValidEducationLevel(educationLevel), MESSAGE_CONSTRAINTS);
        this.educationLevel = educationLevel;
    }

    /**
     * Returns true if a given string is a valid education level.
     */
    public static boolean isValidEducationLevel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Education)) {
            return false;
        }

        Education otherEducation = (Education) other;
        return educationLevel.equals(otherEducation.educationLevel);
    }

    @Override
    public int hashCode() {
        return educationLevel.hashCode();
    }

    @Override
    public String toString() {
        return educationLevel;
    }
}
