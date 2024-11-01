package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a fee amount in the address book.
 */
public abstract class Fee {

    public static final String MESSAGE_CONSTRAINTS = """
            should adhere to the following constraints:
            1. at most 2 decimal places
            """;

    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";

    public final double value;

    /**
     * Constructs a {@code Fee}.
     *
     * @param fee A valid fee.
     */
    public Fee(String fee) {
        requireNonNull(fee);
        checkArgument(isValidFee(fee), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(fee);
    }

    /**
     * Returns true if a given string is a valid fee.
     */
    public static boolean isValidFee(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Fee)) {
            return false;
        }

        Fee otherFee = (Fee) other;
        return value == otherFee.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

}
