package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.commons.util.AppUtil.checkArgument;

import seedu.hireme.logic.validator.RoleValidator;

/**
 * Represents an Internship's role in the internship application.
 * Guarantees: immutable; the role value is validated upon construction.
 */
public class Role {

    /**
     * Message constraints that define the valid format for the role.
     * The role should only contain alphanumeric characters, spaces, and forward slashes (/),
     * and it should not be blank.
     */
    public static final String MESSAGE_CONSTRAINTS = "Roles should only contain alphanumeric "
            + "characters, spaces, and forward slashes (/), and it should not be blank.";
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
        boolean isValidRole = RoleValidator.of().validate(role);
        checkArgument(isValidRole, MESSAGE_CONSTRAINTS);

        this.value = role.trim();
    }

    /**
     * Returns a string representation of the role.
     *
     * @return The role as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this role to another object.
     *
     * @param other The object to compare with.
     * @return True if the other object is an instance of {@code Role} and has the same value, false otherwise.
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
     * @return The hash code of the role value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
