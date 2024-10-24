package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's role in the system.
 * Guarantees: is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
        "Role should only be \"client\" or \"employee\"";

    /**
     * 2 possible roles.
     */
    enum UserRole {
        CLIENT,
        EMPLOYEE
    }

    private UserRole value;

    /**
     * Constructs a {@code Role}.
     */
    public Role(String role) {
        requireNonNull(role);
        role = role.toLowerCase();
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        switch (role) {
        case "client":
            this.value = UserRole.CLIENT;
            break;
        case "employee":
            this.value = UserRole.EMPLOYEE;
            break;
        default:
            break;
        }
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.equals("client") || test.equals("employee");
    }

    /**
     * Method to get the enumerated value.
     * @return Value as a string.
     */
    public String getValue() {
        return String.valueOf(value).toLowerCase();
    }

    @Override
    public String toString() {
        return "Role: " + getValue();
    }
}
