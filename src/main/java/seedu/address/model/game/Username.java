package seedu.address.model.game;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a username in a Game.
 */
public class Username {

    public static final String MESSAGE_CONSTRAINTS =
            "Username should not be blank";

    /*
     * Regex expression matches Strings that contain at least one non-whitespace character.
     */
    private static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String username;

    /**
     * Constructs a {@code Username}.
     *
     * @param username a valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_CONSTRAINTS);
        this.username = username;
    }

    /**
     * Returns true if a given string is a valid Username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Getter for username field.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Username)) {
            return false;
        }

        Username otherName = (Username) other;
        return username.equals(otherName.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return username;
    }

}
