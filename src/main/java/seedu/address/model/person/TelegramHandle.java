package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's telegram handle in the address book.
 * Guarantees: immutable; is always valid
 */
public class TelegramHandle {

    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handles should begin with '@', and contain characters a-z or A-Z, digits 0-9 or underscores. "
                    + "It should also be between 5 and 32 characters (excluding @)";
    public static final String VALIDATION_REGEX = "@[a-zA-Z0-9_]{5,32}";
    public final String value;

    /**
     * Constructs a telegram handle
     * @param telegramHandle A valid telegram handle (non-null)
     */
    public TelegramHandle(String telegramHandle) {
        requireNonNull(telegramHandle);
        checkArgument(isValidTelegramHandle(telegramHandle), MESSAGE_CONSTRAINTS);
        value = telegramHandle;
    }

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
        if (!(other instanceof TelegramHandle)) {
            return false;
        }

        TelegramHandle otherTelegramHandle = (TelegramHandle) other;
        return value.equals(otherTelegramHandle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
