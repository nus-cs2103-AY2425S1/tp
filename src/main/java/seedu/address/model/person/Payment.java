package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's payment status in the address book.
 * Guarantees: immutable;
 */
public class Payment {

    public static final String MESSAGE_CONSTRAINTS = "Payment should be an negative, zero, or "
            + "positive integer \n'$' is not required \nLeading zero are allowed but not recommended";
    public static final String VALIDATION_REGEX = "^-?[0-9]\\d*|0$\n";
    public final String overdueAmount;

    /**
     * Constructs an {@code Payment}.
     *
     * @param overdueAmount Boolean for payment status
     */
    public Payment(String overdueAmount) {
        requireNonNull(overdueAmount);
        this.overdueAmount = overdueAmount;
    }

    /**
     * Returns true if a given string is a valid payment status (either true or false).
     */
    public static Boolean isValidPayment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Current payment overdue: $" + overdueAmount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Payment)) {
            return false;
        }

        Payment otherPayment = (Payment) other;
        return overdueAmount.equals(otherPayment.overdueAmount);
    }

    @Override
    public int hashCode() {
        return overdueAmount.hashCode();
    }

}

