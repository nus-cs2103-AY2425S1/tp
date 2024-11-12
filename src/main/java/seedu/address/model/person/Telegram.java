package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's telegram in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegram(String)}
 */
public class Telegram {

    public static final String MESSAGE_CONSTRAINTS = "Telegram must start with a '@', can only contain '_' & "
            + "alphanumeric characters and must not be blank";

    /*
     * The first character of the telegram must not be a whitespace and must contain '@',
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "@[a-zA-Z0-9_]+";

    public final String value;

    /**
     * Constructs an {@code Address}.
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

    /**
     * Compares the {@code value} of this Telegram object against another Telegram object.
     * Comparison is done using String::CompareTo method.
     */
    public int compareTo(Telegram otherTelegram) {
        requireNonNull(otherTelegram);
        return this.value.toLowerCase().compareTo(otherTelegram.value.toLowerCase());
    }
}
