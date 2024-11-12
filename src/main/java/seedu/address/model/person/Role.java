package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's Role in EduContacts.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    /**
     * Enum representing the valid role types for a person.
     * The available role types are "Student" and "Tutor".
     *
     * <p>Each enum constant has a corresponding string representation that can be used
     * to compare roles, retrieve the role, and validate role values.</p>
     */
    public enum RoleType {
        STUDENT("Student"),
        TUTOR("Tutor");
        private final String role;
        /**
         * Constructs a {@code RoleType} enum constant with the specified role.
         *
         * @param role The string representation of the role type (e.g., "Student" or "Tutor").
         */
        RoleType(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }

        /**
         * Checks if the given string is a valid role by comparing it to the available role types.
         *
         * @param test The string to be checked against the valid role types.
         * @return {@code true} if the string matches any valid role type, {@code false} otherwise.
         */
        public static boolean isValidRole(String test) {
            for (RoleType role : RoleType.values()) {
                if (role.role.equalsIgnoreCase(test)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Your role should either be a student or tutor.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z]+$";
    public final RoleType role;



    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        this.role = RoleType.valueOf(role.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(VALIDATION_REGEX) && RoleType.isValidRole(test);
    }
    @Override
    public String toString() {
        return role.getRole();
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
        return role.equals(otherRole.role);
    }
    @Override
    public int hashCode() {
        return role.hashCode();
    }
}
