package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's experience in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExperience(String)}
 */
public class Experience {

    public static final String MESSAGE_CONSTRAINTS =
            "Experience should not be blank and should only contain alphanumeric characters and spaces.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    /**
     * Constructs a {@code Experience}.
     *
     * @param experience A valid experience.
     */
    public Experience(String experience) {
        requireNonNull(experience);
        checkArgument(isValidExperience(experience), MESSAGE_CONSTRAINTS);
        value = experience;
    }

    /**
     * Returns true if a given string is a valid experience.
     */
    public static boolean isValidExperience(String test) {
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

        if (!(other instanceof Experience)) {
            return false;
        }

        Experience otherExperience = (Experience) other;
        return value.equals(otherExperience.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
