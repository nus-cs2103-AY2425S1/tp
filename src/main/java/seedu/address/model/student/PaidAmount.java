package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's paid tuition fee in the address book.
 */
public class PaidAmount extends Fee {

    public static final double MAX_VALUE = 9999999.99;

    public static final String MESSAGE_CONSTRAINTS = "PaidAmount "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is between the range of 0.00 to " + MAX_VALUE;

    /**
     * Constructs a {@code PaidAmount}.
     *
     * @param paid A valid paid.
     */
    public PaidAmount(String paid) {
        super(paid);
    }

    /**
     * Constructs a {@code PaidAmount}
     * with a default {@code value} of 0.0
     */
    public PaidAmount() {
        super("0");
    }

    /**
     * Returns true if a given string is a valid paid amount.
     */
    public static boolean isValidPaidAmount(String test) {
        if (!Fee.isValidFee(test)) {
            return false;
        }

        double paidAmount = Double.parseDouble(test);
        return paidAmount >= 0 && paidAmount <= MAX_VALUE;
    }

    /**
     * Returns a new {@code PaidAmount} with the updated value by adding the specified amount
     * to the current paid amount.
     *
     * @param amount The amount to be added to the current paid amount.
     * @return A new {@code PaidAmount} object with the updated total amount.
     */
    public PaidAmount updateValue(SettleAmount amount) {
        return new PaidAmount(Double.toString(super.value + amount.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaidAmount)) {
            return false;
        }

        PaidAmount otherPaidAmount = (PaidAmount) other;
        return value == otherPaidAmount.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, PaidAmount.class);
    }
}
