package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should be in the format: (+XXX) 123 456 789 [notes]\n"
                    + "where:\n"
                    + "- Country code (+XXX) is optional and X must be 1-3 digits\n"
                    + "- Main phone number must contain only digits with optional single spaces between numbers\n"
                    + "- [notes] is optional and must be maximum 10 characters";

    // Regex breakdown:
    // ^                        - Start of string
    // (?:\(\+\d{1,3}\)\s)?    - Optional country code in format (+X) to (+XXX)
    // \d+(?:\s\d+)*           - Main phone number with optional single spaces between digits
    // (?:\s\[.{1,10}\])?      - Optional notes in [] with max 10 chars
    // $                        - End of string
    public static final String VALIDATION_REGEX =
            "^(?:\\(\\+\\d{1,3}\\)\\s)?\\d+(?:\\s\\d+)*(?:\\s\\[.{1,10}\\])?$";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
