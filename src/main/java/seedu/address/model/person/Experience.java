package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's work experience in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExperience(String)}
 */
public class Experience {

    public static final String MESSAGE_CONSTRAINTS =
            "Experience can take any values, and it should not be blank";

    /*
     * The first character of the experience must not be a whitespace,
     * and it should only contain alphanumeric characters and spaces.
     */
    public static final String VALIDATION_REGEX = "^[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Experience}.
     *
     * @param experience A valid experience description.
     */
    public Experience(String experience) {
        requireNonNull(experience);
        assert experience != null : "Experience cannot be null";
        checkArgument(isValidExperience(experience), MESSAGE_CONSTRAINTS);
        value = experience;
    }

    /**
     * Returns true if a given string is a valid experience description.
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
        return other == this // if same object
                || (other instanceof Experience // instanceof to handle nulls
                && value.equals(((Experience) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
