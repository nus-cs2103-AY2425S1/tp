package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's income in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncome(String)}
 */
public class Income {

    public static final String MESSAGE_CONSTRAINTS =
            "Income is a non-negative floating point number with up to 2 decimal places e.g. 2.01, 0, 0.1";

    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";

    public static final String EMPTY_VALUE_STRING = "0";

    private final String value;

    /**
     * Constructs an {@code Income}.
     * @param value The value of a peron's income
     */
    public Income(String value) {
        checkArgument(isValidIncome(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public double getDoubleValue() {
        return Double.parseDouble(value);
    }

    public double toDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return String.format("$%.2f", toDouble());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Income otherIncome)) {
            return false;
        }

        return toDouble() == otherIncome.toDouble();
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

    /**
     * Returns true if a given string is a valid income.
     * @param test A string to be tested
     * @return {@code true} if the string is valid, {@code false} otherwise
     */
    public static boolean isValidIncome(String test) {
        requireNonNull(test);

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
