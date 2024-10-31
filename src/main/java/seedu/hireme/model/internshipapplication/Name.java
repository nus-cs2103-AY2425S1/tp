package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.commons.util.AppUtil.checkArgument;

import seedu.hireme.logic.validator.NameValidator;

/**
 * Represents a Company's name in the internship book.
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, underscores and spaces, and it should not be blank";

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
        checkArgument(NameValidator.of().validate(name), MESSAGE_CONSTRAINTS);
        this.value = name.trim();
    }


    /**
     * Returns a string representation of the name.
     *
     * @return the name as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this name to another object.
     *
     * @param other the object to compare.
     * @return true if the object is an instance of {@code Name} and has the same value, false otherwise.
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
     * @return the hash code of the name value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
