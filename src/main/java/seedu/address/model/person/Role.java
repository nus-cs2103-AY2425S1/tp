package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Internship's role in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #validate(String)}.
 */
public class Role implements Validatable {

    public static final String MESSAGE_CONSTRAINTS =
            "Role should only contain alphanumeric characters, single spaces between words, "
                    + "and should not start or end with spaces or special characters.";

    /*
     * Roles should start with an alphanumeric character, and can contain single spaces between words.
     * No leading/trailing spaces or special characters, and no multiple consecutive spaces are allowed.
     */
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum} ]*(?: [\\p{Alnum} ]+)*$";

    private final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     * @throws NullPointerException if the {@code role} is null.
     * @throws IllegalArgumentException if the {@code role} does not satisfy the constraints.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(validate(role), MESSAGE_CONSTRAINTS);
        this.value = role;
    }

    /**
     * Returns the string representation of this role.
     *
     * @return the role as a string.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a string representation of the role.
     *
     * @return the role as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this role to another object.
     *
     * @param other the object to compare.
     * @return true if the object is an instance of {@code Role} and has the same value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equalsIgnoreCase(otherRole.value);
    }

    /**
     * Returns the hash code of the role.
     *
     * @return the hash code of the role value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Validates the given string as a role.
     * Implements the {@code Validatable} interface's method.
     *
     * @param test the string to be validated.
     * @return true if the string is a valid role, false otherwise.
     */
    @Override
    public boolean validate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
