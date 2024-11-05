package seedu.eventtory.model.commons.name;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.commons.util.AppUtil.checkArgument;

/**
 * Represents a name in EventTory.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final String MESSAGE_CONSTRAINTS =
            "Names cannot contain '/' and should not be blank";

    /*
     * The first character of a name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Forward slashes are also not allowed as they conflict with command syntax.
     */
    public static final String VALIDATION_REGEX = "^[^\\s/][^/]*$";

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

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

