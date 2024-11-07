package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's income in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncome(double)}
 */
public class Income {

    public static final String MESSAGE_CONSTRAINTS = "Income should be a non-negative decimal number";

    private final double value;

    /**
     * Constructs an {@code Income}.
     *
     * @param value The value of a person's income
     */
    public Income(double value) {
        checkArgument(isValidIncome(value), MESSAGE_CONSTRAINTS);
        this.value = roundTwoDecimalPlaces(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Income otherIncome)) {
            return false;
        }

        return value == otherIncome.value;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

    /**
     * Returns true if a given value is a valid income.
     * @param test A double value to be tested
     * @return {@code true} if the value is valid, {@code false} otherwise
     */
    public static boolean isValidIncome(double test) {
        return test >= 0;
    }

    private static double roundTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
