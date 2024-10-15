package tutorease.address.model.person;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; its value is either "Guardian" or "Student"
 */
public class Role {
    public static final String GUARDIAN = "Guardian";
    public static final String STUDENT = "Student";
    public static final String MESSAGE_CONSTRAINTS = "Roles can take 'Guardian' or 'Student', and it should not be "
            + "blank";
    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid name, either GUARDIAN or STUDENT.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
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
        return GUARDIAN.equals(role) || STUDENT.equals(role);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
