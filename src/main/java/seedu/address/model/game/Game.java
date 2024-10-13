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

    /**
     * Constructs a {@code Game} without a username.
     *
     * @param gameName A valid Game name.
     */
    public Game(String gameName) {
        requireNonNull(gameName);
        checkArgument(isValidGameName(gameName), MESSAGE_CONSTRAINTS);
        this.gameName = gameName;
        this.username = null;
    }

    /**
     * Constructs a {@code Game} with a username.
     *
     * @param gameName A valid Game name.
     */
    public Game(String gameName, String username) {
        requireNonNull(gameName);
        checkArgument(isValidGameName(gameName), MESSAGE_CONSTRAINTS);
        this.gameName = gameName;
        this.username = new Username(username);
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
