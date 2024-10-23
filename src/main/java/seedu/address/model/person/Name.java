package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
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

    /**
     * Identifies if the name is the same ignoring case sensitivity
     * @param other - the other Name in question
     * @return true or false
     */
    public boolean equalsIgnoreCase(Name other) {
        if (other == null) {
            return false;
        }
        return fullName.equalsIgnoreCase(other.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    /**
     * Compares the name of another person contact in alphabetical order.
     *
     * @param otherName Name of another person contact
     * @return Integer status of the comparison
     */
    public int compareTo(Name otherName) {
        return fullName.toLowerCase().compareTo(otherName.fullName.toLowerCase());
    }

}
