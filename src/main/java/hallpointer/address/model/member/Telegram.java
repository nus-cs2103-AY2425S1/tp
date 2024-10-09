package hallpointer.address.model.member;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Member's telegram in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegram(String)}
 */
public class Telegram {


    public static final String MESSAGE_CONSTRAINTS =
            "Telegram must only contain alphanumeric characters or underscores"
                    + "and be between 5 to 32 characters long\n"
                    + "@ is not required as it is implicitly assumed to be there";
    public static final String VALIDATION_REGEX = "(\\d+){3}";
    public final String value;

    /**
     * Constructs a {@code Telegram}.
     *
     * @param telegram A valid telegram.
     */
    public Telegram(String telegram) {
        requireNonNull(telegram);
        checkArgument(isValidTelegram(telegram), MESSAGE_CONSTRAINTS);
        value = telegram;
    }

    /**
     * Returns true if a given string is a valid telegram.
     */
    public static boolean isValidTelegram(String test) {
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
        if (!(other instanceof Telegram)) {
            return false;
        }

        Telegram otherTelegram = (Telegram) other;
        return value.equals(otherTelegram.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
