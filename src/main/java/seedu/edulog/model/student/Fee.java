package seedu.edulog.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.AppUtil.checkArgument;

/**
 * Represents a student tuition fee
 */
public class Fee {
    public static final String MESSAGE_CONSTRAINTS = "Fee should only contain digits and be less than 1 million.";
    public static final String VALIDATION_REGEX = "^\\d+$";
    public static final int LIMIT = 1000000;

    public final int value;

    /**
     * Construct a {@code Fee}
     */
    public Fee(String fee) {
        requireNonNull(fee);
        checkArgument(isValidFee(fee), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(fee);
    }

    /**
     * Construct a {@code Fee}
     */
    public Fee(int fee) {
        checkArgument(isValidFee(fee), MESSAGE_CONSTRAINTS);
        this.value = fee;
    }

    /**
     * Return true if test is a valid string to parse
     */
    public static boolean isValidFee(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX) && Integer.parseInt(test) < LIMIT;
    }

    /**
     * Return true if fee is a valid int
     * @return
     */
    public static boolean isValidFee(int fee) {
        return 0 < fee && fee < LIMIT;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Fee)) {
            return false;
        }

        Fee otherFee = (Fee) other;
        return otherFee.value == this.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    public String getFormattedString() {
        return toString();
    }
}
