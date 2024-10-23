package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces and hyphens.\n"
                    + "Only 1 space or hyphen is allowed between each alphanumeric character. It should not be blank.";

    public static final String NO_NAME = "__No_Name__";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]+([ -][\\p{Alnum}]+)*\\s*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        if (name.equals(NO_NAME)) {
            fullName = "";
        } else {
            checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
            fullName = name;
        }
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
        // so "Bob" is the same as "bob"
        return fullName.trim().equalsIgnoreCase(otherName.fullName.trim());
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
