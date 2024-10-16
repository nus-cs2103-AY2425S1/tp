package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's hours worked in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHours(String)}
 */
public class Hours {


    public static final String MESSAGE_CONSTRAINTS =
            "Hours should be non-negative, and in numerics.";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d+)?$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Hours(String phone) {
        requireNonNull(phone);
        checkArgument(isValidHours(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid number of hours.
     */
    public static boolean isValidHours(String test) {
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
        if (!(other instanceof Hours)) {
            return false;
        }

        Hours otherHours = (Hours) other;
        return value.equals(otherHours.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
