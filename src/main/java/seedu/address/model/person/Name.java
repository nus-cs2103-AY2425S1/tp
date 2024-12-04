package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #hasValidChars(String)} and {@link #isValidLength(String)}.
 */
public class Name {

    public static final String CHAR_MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, hyphens commas "
                    + "or apostrophes, and it should not be blank";

    public static final String LENGTH_MESSAGE_CONSTRAINTS =
            "Names should only contain up to 300 characters for ease of storage.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CHAR_VALIDATION_REGEX = "[\\-,'\\p{Alnum}][\\-,'\\p{Alnum} ]*";
    public static final String LENGTH_VALIDATION_REGEX = "[^\n]{1,300}";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        name = name.strip();
        checkArgument(hasValidChars(name), CHAR_MESSAGE_CONSTRAINTS);
        checkArgument(isValidLength(name), LENGTH_MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string only contains valid characters.
     */
    public static boolean hasValidChars(String test) {
        return test.matches(CHAR_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is 1 to 300 characters long.
     */
    public static boolean isValidLength(String test) {
        return test.matches(LENGTH_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is valid.
     */
    public static boolean isValidName(String test) {
        return isValidLength(test) && hasValidChars(test);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
