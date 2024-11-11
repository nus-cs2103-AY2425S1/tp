package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the amount of fees that a student has paid/has to pay.
 * Different from payment class as this represents the action of student making the payment
 * or the action fo recording new fees for the student
 * Guarantees: immutable;
 */
public class Fees {

    public static final String MESSAGE_CONSTRAINTS = "Fees must be an positive integer amount";
    public static final String VALIDATION_REGEX = "^[0-9]\\d*|0$\n";
    public final String value;

    /**
     * Constructs an {@code Fees}.
     *
     * @param fees String that represents a positive integer amount
     */
    public Fees(String fees) {
        requireNonNull(fees);
        assert fees.matches("\\d+") : "The string should represent an positive integer";
        this.value = fees;
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


