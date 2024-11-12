package seedu.address.model.commons;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid name format. Names must meet the following constraints:\n"
                    + "1. Only alphanumeric characters, spaces, and the terms "
                    + "' s/o ' (son of) or ' d/o ' (daughter of) are allowed.\n"
                    + "2. Use ' s/o ' and ' d/o ' to specify parentage, with alphanumeric characters "
                    + "both before and after the terms.\n"
                    + "3. The terms are case insensitive.\n"
                    + "4. The terms can only be used once.\n"
                    + "5. The name cannot be blank.\n"
                    + "Example: n/John Doe s/o Jane Doe";

    /*
     * The first character of the address must not be a whitespace, otherwise " " (a blank string)
     * becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "[\\p{Alnum}][\\p{Alnum} ]*(?i)( s/o\\s+\\p{Alnum}+| d/o\\s+\\p{Alnum}+)?(?-i)[\\p{Alnum} ]*";

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
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
