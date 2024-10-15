package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's payment status in the address book.
 * Guarantees: immutable;
 */
public class Payment {

    public static final String MESSAGE_CONSTRAINTS = "Payment must be either paid (true) or not paid (false).";
    public static final String VALIDATION_REGEX = "^(true|false)$";
    public final Boolean hasPaid;

    /**
     * Constructs an {@code Payment}.
     *
     * @param hasPaid Boolean for payment status
     */
    public Payment(Boolean hasPaid) {
        requireNonNull(hasPaid);
        this.hasPaid = hasPaid;
    }

    /**
     * Constructs an {@code Payment}.
     * Set to false as default
     */
    public Payment() {
        this.hasPaid = false;
    }

    /**
     * Returns true if a given string is a valid payment status (either true or false).
     */
    public static Boolean isValidPayment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return hasPaid ? "Paid" : "Not Paid";
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
        return hasPaid.equals(otherPayment.hasPaid);
    }

    @Override
    public int hashCode() {
        return hasPaid.hashCode();
    }

}

