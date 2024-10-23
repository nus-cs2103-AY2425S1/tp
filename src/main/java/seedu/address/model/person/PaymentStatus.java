package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's payment status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPaymentStatus(String)}
 */
public class PaymentStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Payment Status should be 'pending', 'partial', 'paid', 'late' (case insensitive)";
    public static final String NO_PAYMENT_STATUS = "__No_Payment_Status__";
    private static final String LATE = "late";
    private static final String PAID = "paid";
    private static final String PARTIAL = "partial";
    private static final String PENDING = "pending";
    public final String value;

    /**
     * Constructs a {@code PaymentStatus}.
     *
     * @param status A valid payment status.
     */
    public PaymentStatus(String status) {
        requireNonNull(status);
        if (status.equals(NO_PAYMENT_STATUS)) {
            value = parseStatus("");
        } else {
            checkArgument(isValidPaymentStatus(status), MESSAGE_CONSTRAINTS);
            value = parseStatus(status);
        }
    }

    /**
     * Returns true if the given string is a valid payment status.
     */
    public static boolean isValidPaymentStatus(String test) {
        return test.equalsIgnoreCase(PAID)
                || test.equalsIgnoreCase(PENDING)
                || test.equalsIgnoreCase(PARTIAL)
                || test.equalsIgnoreCase(LATE);
    }

    /**
     * Parses the payment status string into a boolean.
     * @param status The payment status string.
     * @return The payment status as a string.
     */
    private static String parseStatus(String status) {
        switch (status.toLowerCase()) {
        case PENDING:
            return PENDING;
        case PARTIAL:
            return PARTIAL;
        case PAID:
            return PAID;
        case LATE:
            return LATE;
        default:
            return status;
        }

    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
