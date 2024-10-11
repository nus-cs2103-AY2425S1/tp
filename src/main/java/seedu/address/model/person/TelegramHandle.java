package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's telegram handle in the address book.
 * Guarantees: immutable; is always valid
 */
public class TelegramHandle {
    public final String value;

    /**
     * Constructs a telegram handle
     * @param telegramHandle A valid telegram handle (non-null)
     */
    public TelegramHandle(String telegramHandle) {
        requireNonNull(telegramHandle);
        value = telegramHandle;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TelegramHandle //instanceof handles nulls
                && value.equals(((TelegramHandle) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
