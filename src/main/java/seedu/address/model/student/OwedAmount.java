package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's owed tuition fee in the address book.
 */
public class OwedAmount extends Fee {
    public static final String MESSAGE_CONSTRAINTS = "Owed "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is non-negative number";

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

    public static boolean isValidOwedAmount(String test) {
        return Fee.isValidFee(test);
    }

    /**
     * Returns a new {@code OwedAmount} with the increased value by adding the specified amount
     * to the current owed amount.
     *
     * @param value The amount to be added to the current owed amount.
     * @return A new {@code OwedAmount} object with the updated owed amount.
     */
    public OwedAmount increaseValue(double value) {
        return new OwedAmount(Double.toString(super.value + value));
    }

    /**
     * Returns a new {@code OwedAmount} with the decreased value by subtracting the specified amount
     * from the current owed amount.
     *
     * @param value The amount to be subtracted from the current owed amount.
     * @return A new {@code OwedAmount} object with the updated owed amount.
     */
    public OwedAmount decreaseValue(double value) {
        return new OwedAmount(Double.toString(super.value - value));
    }

    /**
     * Compares the specified value with the current owed amount.
     *
     * @param value The value to compare against the current owed amount.
     * @return {@code true} if the specified value is greater than the current owed amount, {@code false} otherwise.
     */
    public boolean isGreater(double value) {
        return value > super.value;
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
