package seedu.address.model.wedding;

import seedu.address.model.person.Name;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Role {

    public static final String MESSAGE_CONSTRAINTS = "Role should contain alphabets only.";
    public static final String VALIDATION_REGEX = "^[A-Za-z]+$\n";
    private String fullRole;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        fullRole = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullRole;
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

        Role otherRole = (Role) other;
        return fullRole.equals(otherRole.fullRole);
    }

    @Override
    public int hashCode() {
        return fullRole.hashCode();
    }
}
