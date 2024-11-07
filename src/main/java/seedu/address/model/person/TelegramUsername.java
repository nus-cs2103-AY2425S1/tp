package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram Username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class TelegramUsername {
    public static final String MESSAGE_CONSTRAINTS =
            "Telegram Usernames should only contain alphanumeric characters and underscores, "
                    + "and be between 5 to 32 characters.";

    /*
     * The first character of the address must be an alphabet.
     * "_____" or "12345" are invalid usernames.
     */
    private static final String VALIDATION_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{4,31}$";

    // the only situation where the telegram username is null is when user did not input telegram tag
    public final String telegramUsername;

    /**
     * Constructs a {@code TelegramUsername}.
     *
     * @param username A valid telegram username.
     */
    public TelegramUsername(String username) {
        if (username != null) {
            checkArgument(isValidUsername(username), MESSAGE_CONSTRAINTS);
        }
        telegramUsername = username;
    }

    /**
     * Checks whether there is a telegram username.
     *
     * @return {@code true} if there is a telegram username, {@code false} if empty.
     */
    public boolean hasUsername() {
        return telegramUsername != null;
    }

    /**
     * Returns true if a given string is a valid telegram username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return telegramUsername;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.person.TelegramUsername)) {
            return false;
        }

        seedu.address.model.person.TelegramUsername otherUsername = (seedu.address.model.person.TelegramUsername) other;
        if (telegramUsername == null) {
            return otherUsername.telegramUsername == null; // return True if both are null
        }
        return telegramUsername.equalsIgnoreCase(otherUsername.telegramUsername);
    }

    @Override
    public int hashCode() {
        if (telegramUsername == null) {
            return 0;
        }
        return telegramUsername.hashCode(); // Might be future concern since Telegram Usernames are case insensitive
        // not sure how hashCodes are computed based on strings, and what the use of these hashcodes are
    }
}
