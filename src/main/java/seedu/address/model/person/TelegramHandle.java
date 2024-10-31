package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's telegram handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegramHandle(String)}
 */
public class TelegramHandle {
    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handles should not contain @, and it should have a length of 5 to 32 characters "
                    + "inclusive, containing alphabets, numbers and/or underscores only";
    public static final String VALIDATION_REGEX = "^[A-Za-z0-9_]{5,32}$";
    public final String value;

    /**
     * Constructs a {@code Telegram}.
     *
     * @param telegram A valid telegram handle.
     */
    public TelegramHandle(String telegram) {
        requireNonNull(telegram);
        checkArgument(isValidTelegramHandle(telegram), MESSAGE_CONSTRAINTS);
        value = telegram;
    }

    /**
     * Returns true if a given string is a valid telegram handle.
     */
    public static boolean isValidTelegramHandle(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (other instanceof TelegramHandle otherTelegram) {
            return value.equals(otherTelegram.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
