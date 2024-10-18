package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's owed tuition fee in the address book.
 */
public class OwedAmount extends Fee {
    public static final String MESSAGE_CONSTRAINTS = "Owed " + Fee.MESSAGE_CONSTRAINTS;

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
