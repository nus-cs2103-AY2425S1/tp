package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the amount of fees that a student has paid.
 * Different from payment class as this represents the action of student making the payment
 * Guarantees: immutable;
 */
public class FeesPaidByStudent {

    public static final String MESSAGE_CONSTRAINTS = "Fees paid must be an positive integer amount";
    public static final String VALIDATION_REGEX = "^[0-9]\\d*|0$\n";
    public final String value;

    /**
     * Constructs an {@code FeesPaidByStudent}.
     *
     * @param fees String for payment status
     */
    public FeesPaidByStudent(String fees) {
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
        if (!(other instanceof FeesPaidByStudent)) {
            return false;
        }

        FeesPaidByStudent otherFees = (FeesPaidByStudent) other;
        return value.equals(otherFees.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}


