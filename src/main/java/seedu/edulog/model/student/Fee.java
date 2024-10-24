package seedu.edulog.model.student;

import static java.util.Objects.requireNonNull;

/**
 * Represents a student tuition fee
 */
public class Fee {
    public static final String MESSAGE_CONSTRAINTS = "Fee should only contain digits.";
    public static final String VALIDATION_REGEX = "^\\d+$";

    public final int value;


    public Fee(String fee) {
        this.value = Integer.parseInt(fee);
    }

    public Fee(int fee) {
        this.value = fee;
    }

    /**
     * Return true if test is a valid string to parse
     */
    public static boolean isValidFee(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
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
