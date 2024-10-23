package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;

import tutorease.address.commons.util.NumbersUtil;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Represents a fee amount per hour in the lesson schedule.
 */
public class Fee {
    public static final String MESSAGE_CONSTRAINTS = "Fee must be a non-negative integer.";
    private final int value;
    /**
     * Constructs a {@code Fee}.
     *
     * @param value A valid fee amount.
     */
    public Fee(String value) throws ParseException {
        value = value.trim();
        requireNonNull(value);
        checkArgument(isValidFee(value), MESSAGE_CONSTRAINTS);
        this.value = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
    }
    /**
     * Returns true if a given string is a valid fee amount.
     *
     * @param value The fee amount to be checked.
     * @return True if the fee amount is valid, false otherwise.
     */
    public static boolean isValidFee(String value) {
        try {
            int parsedValue = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
            return parsedValue >= 0;
        } catch (ParseException e) {
            return false;
        }
    }
    /**
     * Returns the value string of the fee.
     *
     * @return The value string of the fee.
     */
    public String getValueString() {
        return String.valueOf(value);
    }
    @Override
    public String toString() {
        return "$" + value;
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
        return value == otherFee.value;
    }
}
