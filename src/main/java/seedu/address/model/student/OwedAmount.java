package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's owed tuition fee in the address book.
 */
public class OwedAmount extends Fee {
    public static final double MAX_VALUE = 9999999.99;
    public static final String MESSAGE_CONSTRAINTS = "Owed "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is between the range of 0.00 to " + MAX_VALUE;

    /**
     * Constructs a {@code OwedAmount}.
     *
     * @param owedAmount A valid owed.
     */
    public OwedAmount(String owedAmount) {
        super(owedAmount);
    }

    /**
     * Constructs a {@code Amount}.
     */
    public OwedAmount() {
        super("0");
    }

    /**
     * Returns true if a given string is a valid owed amount.
     */
    public static boolean isValidOwedAmount(String test) {
        if (!Fee.isValidFee(test)) {
            return false;
        }

        double owedAmount = Double.parseDouble(test);
        return owedAmount >= 0 && owedAmount <= MAX_VALUE;
    }

    /**
     * Returns true if a given value is a valid owed amount.
     */
    public static boolean isValidOwedAmount(double test) {
        return test >= 0 && test <= MAX_VALUE;
    }

    /**
     * Returns a new {@code OwedAmount} with the decreased value by subtracting the specified amount
     * from the current owed amount.
     *
     * @param amount The amount to be subtracted from the current owed amount.
     * @return A new {@code OwedAmount} object with the updated owed amount.
     */
    public OwedAmount decreaseValue(SettleAmount amount) {
        return new OwedAmount(Double.toString(super.value - amount.value));
    }

    /**
     * Compares the specified value with the current owed amount.
     *
     * @param amount The value to compare against the current owed amount.
     * @return {@code true} if the specified value is greater than the current owed amount, {@code false} otherwise.
     */
    public boolean isGreater(SettleAmount amount) {
        return amount.value > super.value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OwedAmount)) {
            return false;
        }

        OwedAmount otherOwedAmount = (OwedAmount) other;
        return value == otherOwedAmount.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, OwedAmount.class);
    }
}
