package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a Role in the address book that has a natural ordering.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRoleName(String)}
 */
public class Role implements Comparable<Role> {
    public static final String PRESIDENT = "President";
    public static final String VICE_PRESIDENT = "Vice President";
    public static final String ADMIN = "Admin";
    public static final String MARKETING = "Marketing";
    public static final String EVENTS_INTERNAL = "Events (Internal)";
    public static final String EVENTS_EXTERNAL = "Events (External)";
    public static final String EXTERNAL_RELATIONS = "External Relations";

    private static final int PRESIDENT_INDEX = 1;
    private static final int VICE_PRESIDENT_INDEX = 2;
    private static final int ADMIN_INDEX = 3;
    private static final int MARKETING_INDEX = 4;
    private static final int EVENTS_INTERNAL_INDEX = 5;
    private static final int EVENTS_EXTERNAL_INDEX = 6;
    private static final int EXTERNAL_RELATIONS_INDEX = 7;

    public static final String MESSAGE_CONSTRAINTS = "Role must be one of the following: \n"
            + PRESIDENT_INDEX + ". " + PRESIDENT + "\n"
            + VICE_PRESIDENT_INDEX + ". " + VICE_PRESIDENT + "\n"
            + ADMIN_INDEX + ". " + ADMIN + "\n"
            + MARKETING_INDEX + ". " + MARKETING + "\n"
            + EVENTS_INTERNAL_INDEX + ". " + EVENTS_INTERNAL + "\n"
            + EVENTS_EXTERNAL_INDEX + ". " + EVENTS_EXTERNAL + "\n"
            + EXTERNAL_RELATIONS_INDEX + ". " + EXTERNAL_RELATIONS + "\n";

    public static final String[] AVAILABLE_ROLES = {
        PRESIDENT,
        VICE_PRESIDENT,
        ADMIN,
        MARKETING,
        EVENTS_INTERNAL,
        EVENTS_EXTERNAL,
        EXTERNAL_RELATIONS};

    public final String roleName;

    private final int roleIndex;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    public Role(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName), MESSAGE_CONSTRAINTS);
        this.roleName = toOfficialCase(roleName);
        this.roleIndex = assignRoleIndex(this.roleName);
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

    public int getRoleIndex() {
        return this.roleIndex;
    }

    private String toOfficialCase(String input) {
        assert Role.isValidRoleName(input);
        return Arrays.stream(AVAILABLE_ROLES)
                .filter(role -> role.equalsIgnoreCase(input))
                .reduce("", (roleToReturn, role) -> roleToReturn + role);
    }

    private int assignRoleIndex(String roleName) {
        assert roleName.equals(PRESIDENT) // must be case sensitive
                || roleName.equals(VICE_PRESIDENT)
                || roleName.equals(ADMIN)
                || roleName.equals(MARKETING)
                || roleName.equals(EVENTS_INTERNAL)
                || roleName.equals(EVENTS_EXTERNAL)
                || roleName.equals(EXTERNAL_RELATIONS);

        int errorStatus = -1;

        switch (roleName) {
        case PRESIDENT:
            return PRESIDENT_INDEX;
        case VICE_PRESIDENT:
            return VICE_PRESIDENT_INDEX;
        case ADMIN:
            return ADMIN_INDEX;
        case MARKETING:
            return MARKETING_INDEX;
        case EVENTS_INTERNAL:
            return EVENTS_INTERNAL_INDEX;
        case EVENTS_EXTERNAL:
            return EVENTS_EXTERNAL_INDEX;
        case EXTERNAL_RELATIONS:
            return EXTERNAL_RELATIONS_INDEX;
        default:
            assert false;
            return errorStatus; // if happen, programming error
        }
    }

    @Override
    public int compareTo(Role role) {
        return getRoleIndex() - role.getRoleIndex();
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
