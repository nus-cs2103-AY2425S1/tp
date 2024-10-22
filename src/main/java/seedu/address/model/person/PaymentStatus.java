package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's payment status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPaymentStatus(String)}
 */
public class PaymentStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Payment Status should be 'paid', 'unpaid', 'p, 'u', '0' (for paid), '1' (for unpaid) (case insensitive)";
    public static final String NO_PAYMENT_STATUS = "__No_Payment_Status__";
    private static final String UNPAID = "unpaid";
    private static final String PAID = "paid";
    private static final String U_SHORT = "u";
    private static final String P_SHORT = "p";
    private static final String UNPAID_NUMERIC = "1";
    private static final String PAID_NUMERIC = "0";

    public final boolean isPaid;
    private boolean isEmpty = false;

    /**
     * Constructs a {@code PaymentStatus}.
     *
     * @param status A valid payment status.
     */
    public PaymentStatus(String status) {
        requireNonNull(status);
        if (status.equals(NO_PAYMENT_STATUS)) {
            isPaid = parseStatus("");
            isEmpty = true;
        } else {
            checkArgument(isValidPaymentStatus(status), MESSAGE_CONSTRAINTS);
            isPaid = parseStatus(status);
        }
    }

    /**
     * Returns true if the given string is a valid payment status.
     */
    public static boolean isValidPaymentStatus(String test) {
        return test.equalsIgnoreCase(PAID)
                || test.equalsIgnoreCase(UNPAID)
                || test.equalsIgnoreCase(P_SHORT)
                || test.equalsIgnoreCase(U_SHORT)
                || test.equals(PAID_NUMERIC)
                || test.equals(UNPAID_NUMERIC);
    }

    /**
     * Parses the payment status string into a boolean.
     * @param status The payment status string.
     * @return {@code true} if the status is "complete", {@code false} if it is "in progress".
     */
    private static boolean parseStatus(String status) {
        return status.equalsIgnoreCase(PAID)
                || status.equalsIgnoreCase(P_SHORT)
                || status.equals(PAID_NUMERIC);
    }

    @Override
    public String toString() {
        if (isEmpty) {
            return "";
        }
        return isPaid ? PAID : UNPAID;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PaymentStatus)) {
            return false;
        }

        PaymentStatus otherStatus = (PaymentStatus) other;
        return isPaid == otherStatus.isPaid;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isPaid);
    }

}
