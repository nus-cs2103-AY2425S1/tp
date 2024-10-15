package seedu.address.model.game;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Game in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGameName(String)}
 */
public class Game {

    public static final String MESSAGE_CONSTRAINTS =
            "Games should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String gameName;
    public final Username username;
    public final SkillLevel skillLevel;
    public final Role role;


    /**
     * Constructs a {@code Game}.
     *
     * @param gameName A valid Game name.
     * @param username (Optional) A username.
     * @param skillLevel (Optional) A skill level.
     * @param role (Optional) A role.
     */
    public Game(String gameName, String username, String skillLevel, String role) {
        requireNonNull(gameName);
        checkArgument(isValidGameName(gameName), MESSAGE_CONSTRAINTS);
        this.gameName = gameName;
        this.username = username != null ? new Username(username) : null;
        this.skillLevel = skillLevel != null ? new SkillLevel(skillLevel) : null;
        this.role = role != null ? new Role(role) : null;
    }

    // Overloaded constructors for convenience
    public Game(String gameName) {
        this(gameName, null, null, null);
    }

    public Game(String gameName, String username) {
        this(gameName, username, null, null);
    }

    public Game(String gameName, String username, String skillLevel) {
        this(gameName, username, skillLevel, null);
    }

    /**
     * Returns true if a given string is a valid Game name.
     */
    public static boolean isValidGameName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Game)) {
            return false;
        }

        Game otherGame = (Game) other;
        return gameName.equals(otherGame.gameName);
    }

    @Override
    public int hashCode() {
        return gameName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        if (username == null) {
            return '[' + gameName + ']';
        } else {
            return '[' + gameName + "]: " + username.toString();
        }
    }

}
