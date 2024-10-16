package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role (case-insensitive) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS = "Roles can only take in values part of this list, "
            + "stage manager, sound technician, organiser, artist, promoter";

    /**
     * All the possible valid roles that can be assigned
     */
    public static enum Roles {
        STAGE_MANAGER,
        SOUND_TECHNICIAN,
        ORGANISER,
        ARTIST,
        PROMOTER
    }

    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        // Allow only alphabetical characters and whitespaces
        if (!test.matches("^[ A-Za-z]+$")) {
            return false;
        }

        test = test.replace(" ", "_");
        for (Roles role : Roles.values()) {
            if (role.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
