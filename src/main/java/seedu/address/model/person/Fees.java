package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's fees in the address book.
 */
public class Fees {

    public static final String MESSAGE_CONSTRAINTS =
            "Fees should be a positive integer, only contain numbers, and should be at least 1 digit long.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\d{1,}$";
    public final String value;

    /**
     * Constructs a {@code Fees}.
     */
    public Fees(String fees) {
        requireNonNull(fees);
        checkArgument(isValidFees(fees), MESSAGE_CONSTRAINTS);
        value = fees;
    }

    /**
     * Returns true if a given string is a valid fees.
     */
    public static boolean isValidFees(String test) {
        return test.isEmpty() || test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof ClassId)) {
            return false;
        }

        ClassId otherClassId = (ClassId) other;
        return value.equals(otherClassId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
