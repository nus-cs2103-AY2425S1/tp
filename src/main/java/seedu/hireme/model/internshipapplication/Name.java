package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.commons.util.AppUtil.checkArgument;

import seedu.hireme.logic.validator.NameValidator;

/**
 * Represents a Company's name.
 * Guarantees: immutable; the name is valid as declared in {@link NameValidator}.
 */
public class Name {

    /**
     * The message that describes the constraints for a valid name format.
     * Names should contain only alphanumeric characters, spaces, and the following special characters:
     * underscore (_), ampersand (&), forward slash (/), dot (.), colon (:), and parentheses ().
     * Names should not be blank.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, underscores (_), ampersand (&), "
                    + "forward slash (/), dot (.), colon (:), and parentheses (). Names should not be blank.";

    private final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     * @throws NullPointerException if the {@code name} is null.
     * @throws IllegalArgumentException if the {@code name} does not satisfy the constraints.
     */
    public Name(String name) {
        requireNonNull(name);
        boolean isValidName = NameValidator.of().validate(name);
        checkArgument(isValidName, MESSAGE_CONSTRAINTS);

        this.value = name.trim();
    }

    /**
     * Returns a string representation of the name.
     *
     * @return The name as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this name is equal to another object.
     *
     * @param other The object to compare.
     * @return True if the object is an instance of {@code Name} and has the same value, ignoring case.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return value.equalsIgnoreCase(otherName.value);
    }

    /**
     * Returns the hash code of the name.
     *
     * @return The hash code of the name value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
