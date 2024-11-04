package seedu.address.model.student;

/**
 * Represents amount student paid to settle owed fees.
 */
public class SettleAmount extends Fee {

    public static final double MAX_VALUE = 9999999.99;

    public static final String MESSAGE_CONSTRAINTS = "Amount "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is between the range of 0.00 to " + MAX_VALUE;
    public SettleAmount(String amount) {
        super(amount);
    }

    public SettleAmount(double amount) {
        super(Double.toString(amount));
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

    public double getValue() {
        return value;
    }
}
