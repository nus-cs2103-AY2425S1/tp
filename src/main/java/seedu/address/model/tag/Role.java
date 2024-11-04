package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a Role in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRoleName(String)}
 */
public class Role {

    public static final String PRESIDENT = "President";
    public static final String VICE_PRESIDENT = "Vice President";
    public static final String ADMIN = "Admin";
    public static final String MARKETING = "Marketing";
    public static final String EVENTS_INTERNAL = "Events (Internal)";
    public static final String EVENTS_EXTERNAL = "Events (External)";
    public static final String EXTERNAL_RELATIONS = "External Relations";

    public static final String MESSAGE_CONSTRAINTS = "Role must be one of the following: \n"
            + "1. " + PRESIDENT + "\n"
            + "2. " + VICE_PRESIDENT + "\n"
            + "3. " + ADMIN + "\n"
            + "4. " + MARKETING + "\n"
            + "5. " + EVENTS_INTERNAL + "\n"
            + "6. " + EVENTS_EXTERNAL + "\n"
            + "7. " + EXTERNAL_RELATIONS + "\n";
    public static final String[] AVAILABLE_ROLES = {
        PRESIDENT,
        VICE_PRESIDENT,
        ADMIN,
        MARKETING,
        EVENTS_INTERNAL,
        EVENTS_EXTERNAL,
        EXTERNAL_RELATIONS};

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    public Role(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName), MESSAGE_CONSTRAINTS);
        this.roleName = toOfficialCase(roleName);
    }

    /**
     * Returns true if a given string is a valid role name.
     */
    public static boolean isValidRoleName(String test) {
        requireNonNull(test);
        return Arrays.stream(AVAILABLE_ROLES)
                .map(role -> role.toLowerCase())
                .anyMatch(role -> role.equals(test.trim().toLowerCase()));
    }

    private String toOfficialCase(String input) {
        assert Role.isValidRoleName(input);
        return Arrays.stream(AVAILABLE_ROLES)
                .filter(role -> role.equalsIgnoreCase(input))
                .reduce("", (roleToReturn, role) -> roleToReturn + role);
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
        return roleName.equals(otherRole.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + roleName + ']';
    }

}
