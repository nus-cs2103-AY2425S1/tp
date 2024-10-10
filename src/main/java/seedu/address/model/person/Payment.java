package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Student's payment status in the address book.
 * Guarantees: immutable;
 */
public class Payment {

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

