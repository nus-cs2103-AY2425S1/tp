package tutorease.address.model.person;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces and brackets, and it should not be blank";

    public static final String MESSAGE_CONSTRAINTS_NO_SLASHES = "Names should not contain slashes ('/').";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}()][\\p{Alnum}() ]*";

    public static final String REGEX_NO_SLASHES = "^[^/]*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(hasNoSlash(name), MESSAGE_CONSTRAINTS_NO_SLASHES);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if the given string is a valid name.
     *
     * @param test The string to be tested.
     * @return true if the string matches the valid name format, false otherwise.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string does not contain slashes.
     *
     * @param test The string to be tested.
     * @return true if the string does not contain slashes, false otherwise.
     */
    public static boolean hasNoSlash(String test) {
        return test.matches(REGEX_NO_SLASHES);
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
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
