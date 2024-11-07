package tutorease.address.model.person;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;
/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; its value is either "Guardian" or "Student".
 */
public class Role {
    public static final String GUARDIAN = "Guardian";
    public static final String STUDENT = "Student";
    public static final String MESSAGE_CONSTRAINTS = "Roles can take 'Guardian' or 'Student', and it should not be "
            + "blank.";
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

    /**
     * Returns a valid role string for {@code Role} construction.
     *
     * @param role A valid name, either GUARDIAN or STUDENT.
     */
    public static String createRole(String role) {
        requireNonNull(role);
        final String value;
        if (GUARDIAN.equalsIgnoreCase(role)) {
            value = GUARDIAN;
        } else if (STUDENT.equalsIgnoreCase(role)) {
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
    /**
     * Validates whether a string indicates a valid role(Student or Guardian), and is case-insensitive in nature.
     *
     * @param role A string.
     */
    public static boolean isValidRole(String role) {
        requireNonNull(role);
        return GUARDIAN.equalsIgnoreCase(role) || STUDENT.equalsIgnoreCase(role);
    }

    public String getRoleString() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
