package seedu.address.model.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;

/**
 * Represents a user profile with a name.
 */
public class Profile {
    public static final String MESSAGE_CONSTRAINTS =
            "The profile name can only contain letters (a-z, A-Z), numbers (0-9), and hyphens (-). "
                    + "It must also be 30 characters or less.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9-]+$";
    public final String profileName;

    /**
     * Constructs a {@code Profile}.
     *
     * @param profileName A valid profile name.
     */
    public Profile(String profileName) {
        requireNonNull(profileName);
        checkArgument(isValidProfile(profileName), MESSAGE_CONSTRAINTS);
        this.profileName = profileName;
    }

    /**
     * Checks if the given profile name is valid based on the following criteria:
     * <ul>
     *     <li>The profile name must be alphanumeric (a-z, A-Z, 0-9) or contain hyphens (-).</li>
     *     <li>The profile name must not exceed 30 characters in length.</li>
     * </ul>
     *
     * @param profileName the name of the new profile to validate
     * @return true if the profile name is valid according to the criteria, false otherwise
     */
    public static boolean isValidProfile(String profileName) {
        Pattern pattern = Pattern.compile(VALIDATION_REGEX);
        return profileName.length() <= 30 && pattern.matcher(profileName).matches();
    }

    /**
     * Returns the singleton instance of the {@code EmptyProfile} class.
     *
     * @return An instance of {@code EmptyProfile}.
     */
    public static EmptyProfile getEmptyProfile() {
        return EmptyProfile.getInstance();
    }

    /**
     * A singleton class that represents an empty profile.
     */
    public static class EmptyProfile extends Profile {
        private static final EmptyProfile instance = new EmptyProfile();

        /**
         * Constructs an {@code EmptyProfile} with an arbitrary name.
         */
        private EmptyProfile() {
            super("EMPTY");
        }

        /**
         * Returns the singleton instance of the {@code EmptyProfile} class.
         *
         * @return The single instance of {@code EmptyProfile}.
         */
        private static EmptyProfile getInstance() {
            return instance;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Profile)) {
            return false;
        }

        Profile otherProfile = (Profile) other;
        return profileName.equalsIgnoreCase(otherProfile.profileName);
    }

    @Override
    public int hashCode() {
        return profileName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return profileName;
    }
}

