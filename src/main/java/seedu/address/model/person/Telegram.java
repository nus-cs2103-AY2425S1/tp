package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a Person's telegram username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegram(String)}
 */
public class Telegram {

    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handle must only have characters that are alphanumeric "
                    + "or ‘_’, and cannot be empty";

    public static final String LENGTH_CONSTRAINTS =
            "Telegram handle must be between 5 to 32 characters";

    /*
     * The telegram handle cannot have any whitespaces or be empty, and must
     * only contain characters that are alphanumeric or '_'
     */
    public static final String VALIDATION_REGEX = "^(?=\\S)[a-zA-Z0-9_]+$";

    /*
     * The telegram handle must be between 5-32 characters in length
     */
    public static final int VALIDATION_LENGTH_MIN = 5;
    public static final int VALIDATION_LENGTH_MAX = 32;

    public final String value;

    /**
     * Constructs an {@code Telegram}.
     *
     * @param telegram A valid telegram username.
     */
    public Telegram(String telegram) {
        requireNonNull(telegram);
        checkArgument(isValidTelegram(telegram), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTelegramLength(telegram), LENGTH_CONSTRAINTS);
        value = telegram;
    }

    /**
     * Returns true if a given string is a valid telegram handle character-wise.
     */
    public static boolean isValidTelegram(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid telegram handle length-wise.
     */
    public static boolean isValidTelegramLength(String test) {
        return test.length() >= VALIDATION_LENGTH_MIN && test.length() <= VALIDATION_LENGTH_MAX;
    }

    /**
     * Checks if telegram handle is at least 5 characters long
     * and that it contains no illegal characters. Otherwise
     * throws relevant exception
     * @throws CommandException
     */
    public static void isValidHandle(String tele) throws CommandException {
        if (!isValidTelegramLength(tele)) {
            throw new CommandException(Telegram.LENGTH_CONSTRAINTS);
        } else if (!isValidTelegram(tele)) {
            throw new CommandException(Telegram.MESSAGE_CONSTRAINTS);
        }
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
        if (!(other instanceof Telegram)) {
            return false;
        }

        Telegram otherTelegram = (Telegram) other;
        return value.equalsIgnoreCase(otherTelegram.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
