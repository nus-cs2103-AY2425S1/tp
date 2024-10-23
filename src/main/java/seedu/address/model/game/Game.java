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
    private boolean isFavourite;

    /**
     * Constructs a {@code Game}.
     *
     * @param gameName A valid Game name.
     * @param username A username.
     * @param skillLevel A skill level.
     * @param role A role.
     * @param isFavourite favourite status of the game
     */
    public Game(String gameName, Username username, SkillLevel skillLevel, Role role, boolean isFavourite) {
        requireNonNull(gameName);
        checkArgument(isValidGameName(gameName), MESSAGE_CONSTRAINTS);
        this.gameName = gameName;
        this.username = username;
        this.skillLevel = skillLevel;
        this.role = role;
        this.isFavourite = isFavourite;
    }

    /**
     * Alternate constructor
     * @param gameName the name of the game.
     */
    public Game(String gameName) {
        requireNonNull(gameName);
        checkArgument(isValidGameName(gameName), MESSAGE_CONSTRAINTS);
        this.gameName = gameName;
        this.username = null;
        this.skillLevel = null;
        this.role = null;
        this.isFavourite = false;
    }

    /**
     * Returns true if a given string is a valid Game name.
     */
    public static boolean isValidGameName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getGameName() {
        return this.gameName;
    }

    public Username getUsername() {
        return this.username;
    }

    public SkillLevel getSkillLevel() {
        return this.skillLevel;
    }

    public Role getRole() {
        return this.role;
    }

    public boolean getFavouriteStatus() {
        return this.isFavourite;
    }

    /**
     * Sets a game to "favourite"
     */
    public void setAsFavourite() {
        this.isFavourite = true;
    }

    /**
     * Removes the "favourite" status from a game
     */
    public void removeFavourite() {
        this.isFavourite = false;
    }

    /**
     * Returns true if both games have the same name.
     * This defines a weaker notion of equality between two game objects.
     */
    public boolean isSameGame(Game otherGame) {
        if (otherGame == this) {
            return true;
        }

        return otherGame != null && otherGame.getGameName().equals(this.gameName);
    }

    /**
     * Returns true if both games have the same identity and data fields.
     * This defines a stronger notion of equality between two game objects.
     */
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
