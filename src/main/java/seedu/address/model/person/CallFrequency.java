package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's call frequency in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCallFrequency(String)}
 */
public class CallFrequency {
    public static final String MESSAGE_CONSTRAINTS =
            "Calling frequency is in days and has to be a positive number less than or equal to 7";

    public static final String VALIDATION_REGEX = "^[1-7]$";

    public final String value;

    /**
     * Constructs a {@code CallFrequency}.
     *
     * @param callFrequency A valid date.
     */
    public CallFrequency(String callFrequency) {
        requireNonNull(callFrequency);
        checkArgument(isValidCallFrequency(callFrequency), MESSAGE_CONSTRAINTS);
        value = callFrequency;
    }

    /**
     * Returns true if a given string is a valid call frequency.
     */
    public static boolean isValidCallFrequency(String test) {
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
        if (!(other instanceof CallFrequency)) {
            return false;
        }

        CallFrequency otherCallFrequency = (CallFrequency) other;
        return value.equals(otherCallFrequency.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
