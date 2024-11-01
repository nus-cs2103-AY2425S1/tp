package tutorease.address.model.person;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; its value is either "Guardian" or "Student"
 */
public class Role {
    public static final String GUARDIAN_LOWERCASE = "guardian";
    public static final String STUDENT_LOWERCASE = "student";
    public static final String GUARDIAN = "Guardian";
    public static final String STUDENT = "Student";
    public static final String MESSAGE_CONSTRAINTS = "Roles can take 'Guardian' or 'Student', and it should not be "
            + "blank." + "Also, roles are case-insensitive.";
    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid name, either GUARDIAN or STUDENT.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = createRole(role);
    }

    private String createRole(String role) {
        final String value;
        if (GUARDIAN_LOWERCASE.equals(role.toLowerCase())) {
            value = GUARDIAN;
        } else if (STUDENT_LOWERCASE.equals(role.toLowerCase())) {
            value = STUDENT;
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Role
                && value.equals(((Role) other).value));
    }
    // Validation method
    public static boolean isValidRole(String role) {
        return GUARDIAN_LOWERCASE.equals(role.toLowerCase()) || STUDENT_LOWERCASE.equals(role.toLowerCase());
    }

    public String getRoleString() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
