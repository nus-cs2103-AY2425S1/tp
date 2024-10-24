package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)}
 */
public class Sex {

    public static final String MESSAGE_GUI = "Sex: %1$s";
    public static final String MESSAGE_CONSTRAINTS = "Sex can only take \"M\" or \"F\"";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[MF]$";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param sex A valid sex.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        checkArgument(isValidSex(sex), MESSAGE_CONSTRAINTS);
        value = sex;
    }

    /**
     * Returns true if a given string is a valid sex.
     */
    public static boolean isValidSex(String test) {
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
