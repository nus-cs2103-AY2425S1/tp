package tuteez.model.person;

import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's telegram username in the address book.
 * The person's telegram username is optional.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegramHandle(String)}
 */
public class TelegramUsername {
    public static final String MESSAGE_CONSTRAINTS =
            "Telegram usernames must be at least 5 characters long, and can only contain a-z/A-Z, 0-9, and underscores."
            + " Telegram usernames are case insensitive, and will be in lowercase. It must start with a letter,"
            + " and should not be blank unless the user is editing.";

    public static final String VALIDATION_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{4,31}$";

    public final String telegramUsername;

    /**
     * Constructs a {@code TelegramUsername}.
     * Private constructor to enforce the use of the static factory method.
     * @param username A valid telegram handle.
     */
    private TelegramUsername(String username) {
        this.telegramUsername = username;
    }

    /**
     * Factory method to create a TelegramUsername object with a valid telegram handle.
     * @param username A valid telegram handle.
     * @return A TelegramUsername instance.
     */
    public static TelegramUsername of(String username) {
        requireNonNull(username);
        checkArgument(isValidTelegramHandle(username), MESSAGE_CONSTRAINTS);
        return new TelegramUsername(username.toLowerCase());
    }

    /**
     * Factory method to create a TelegramUsername object with an empty username.
     * This is used to represent the optional nature of the telegram handle.
     * @return A TelegramUsername instance with no username.
     */
    public static TelegramUsername empty() {
        TelegramUsername empty = new TelegramUsername(null);
        assert empty.telegramUsername == null : "Empty username must have null value";
        return empty;
    }

    /**
     * Returns true if a given string is a valid telegram handle.
     * @param test the string to test
     */
    public static boolean isValidTelegramHandle(String test) {
        if (test == null) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (telegramUsername == null) {
            return "";
        }
        return telegramUsername;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TelegramUsername)) {
            return false;
        }

        TelegramUsername otherTeleHandle = (TelegramUsername) other;
        if (telegramUsername == null) {
            return Objects.equals(otherTeleHandle.telegramUsername, null);
        }
        return this.telegramUsername.equals(otherTeleHandle.telegramUsername);
    }

    @Override
    public int hashCode() {
        return telegramUsername == null ? 0 : telegramUsername.hashCode();
    }

}
