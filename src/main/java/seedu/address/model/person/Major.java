package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's major in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(String)}
 */
public class Major {

    public static final String MESSAGE_CONSTRAINTS = "Major can take any values";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Major}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_CONSTRAINTS);
        value = major;
    }

    /**
     * Returns true if a given string is a valid major.
     */
    public static boolean isValidMajor(String test) {
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
        if (!(other instanceof Major)) {
            return false;
        }

        Major otherMajor = (Major) other;
        return value.equals(otherMajor.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
