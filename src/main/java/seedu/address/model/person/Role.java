package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS =
            "The role should be either student or parent, and it should not be blank";

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid phone number.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        roleName = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.toLowerCase().equals("student")
                || test.toLowerCase().equals("parent");
    }

    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.toLowerCase().equals(otherRole.roleName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }



}
