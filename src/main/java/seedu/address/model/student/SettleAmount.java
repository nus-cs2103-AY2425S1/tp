package seedu.address.model.student;

/**
 * Represents amount student paid to settle owed fees.
 */
public class SettleAmount extends Fee {

    public static final double MAX_VALUE = 9999999.99;

    public static final String MESSAGE_CONSTRAINTS = "Amount "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is greater than $0.00 and less than or equal to $" + MAX_VALUE;

    public SettleAmount(String amount) {
        super(amount);
    }

    /**
     * Returns true if a given string is a valid settle amount.
     */
    public static boolean isValidSettleAmount(String test) {
        if (!Fee.isValidFee(test)) {
            return false;
        }

        double settleAmount = Double.parseDouble(test);
        return settleAmount > 0 && settleAmount <= MAX_VALUE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SettleAmount)) {
            return false;
        }

        SettleAmount otherSettleAmount = (SettleAmount) other;

        return value == otherSettleAmount.value;
    }

}
