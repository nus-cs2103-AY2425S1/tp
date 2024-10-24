package seedu.address.model.game;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Describes the Person's skill level in a Game.
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
            "Role should not be blank";

    /*
     * Regex expression matches Strings that contain at least one non-whitespace character.
     */
    private static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String role;

    /**
     * Constructs a {@code Role}.
     *
     * @param role a valid skill level or rank.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        this.role = role;
    }

    /**
     * Returns true if a given string is a valid Role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Getter for role field.
     */
    public String getRole() {
        return role;
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

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return role;
    }

}
