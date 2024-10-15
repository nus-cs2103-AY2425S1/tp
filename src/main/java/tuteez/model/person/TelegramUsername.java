package tuteez.model.person;

import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's telegram username in the address book.
 * The person's telegram username is optional.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegramHandle(String)}
 */
public class TelegramUsername {
    public static final String MESSAGE_CONSTRAINTS =
            "Telegram usernames must be at least 5 characters long, and can only contain a-z/A-Z, 0-9, and underscores."
            + " Telegram usernames are case insensitive, and will be in lowercase. It must start with a letter,"
            + " and should not be blank.";

    public static final String VALIDATION_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{4,31}$";

    public final String telegramHandle;

    /**
     * Constructs a {@code TelegramUsername}.
     * @param username A valid telegram handle.
     */
    public TelegramUsername(String username) {
        requireNonNull(username);
        checkArgument(isValidTelegramHandle(username), MESSAGE_CONSTRAINTS);
        this.telegramHandle = username;
    }

    /**
     * Returns true if a given string is a valid telegram handle.
     * @param test the string to test
     */
    public static boolean isValidTelegramHandle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the telegram handle in lowercase.
     */
    public String toLowerCaseUsername() {
        return telegramHandle.toLowerCase();
    }

    @Override
    public String toString() {
        return toLowerCaseUsername();
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
        return this.toLowerCaseUsername().equalsIgnoreCase(otherTeleHandle.toLowerCaseUsername());
    }

    @Override
    public int hashCode() {
        return toLowerCaseUsername().hashCode();
    }

}
