package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the amount of fees that a student has paid.
 * Guarantees: immutable;
 */
public class Fees {

    public static final String MESSAGE_CONSTRAINTS = "Fees must be an positive integer amount \n"
            + "Leading zeros are allowed but not recommended"
            + "The value zero is allowed, but what's the point?";
    public static final String VALIDATION_REGEX = "^[0-9]\\d*|0$\n";
    public final String value;

    /**
     * Constructs an {@code Fees}.
     *
     * @param balance String for payment status
     */
    public Fees(String balance) {
        requireNonNull(balance);
        this.value = balance;
    }

    /**
     * Returns true if a given string is a valid fees status (either true or false).
     */
    public static Boolean isValidFees(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Balance: " + value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Fees)) {
            return false;
        }

        Fees otherFees = (Fees) other;
        return value.equals(otherFees.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}


