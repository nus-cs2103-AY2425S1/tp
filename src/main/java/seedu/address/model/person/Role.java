package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
            "Roles should only be 'brUdder' or 'mUdder' and it can't be blank.";

    private String role;

    /**
     * Constructs a {@code Role}.
     *
     * @param role a valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        this.role = role;
    }

    /**
     * Returns true if a given role string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.equals("brUdder") || test.equals("mUdder");
    }

    @Override
    public String toString() {
        return this.role;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return this.role.equals(otherRole.role);
    }

    @Override
    public int hashCode() {
        return this.role.hashCode();
    }
}
