package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's coverage amount.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoverageAmount(String)}
 * or {@link #isValidCoverageAmount(double)}
 */
public class CoverageAmount {
    public static final String MESSAGE_CONSTRAINTS = "Coverage amount must be a non-negative numeral with at "
            + "most 2 decimal points.";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public final double value;

    /**
     * Constructs a {@code CoverageAmount} using a {@code String} object.
     *
     * @param coverageAmount The coverageAmount expressed as a String.
     */
    public CoverageAmount(String coverageAmount) {
        requireNonNull(coverageAmount);
        checkArgument(isValidCoverageAmount(coverageAmount), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(coverageAmount);
    }

    /**
     * Constructs a {@code CoverageAmount} using a double.
     *
     * @param coverageAmount The coverageAmount expressed as a double.
     */
    public CoverageAmount(double coverageAmount) {
        checkArgument(isValidCoverageAmount(coverageAmount), MESSAGE_CONSTRAINTS);
        value = coverageAmount;
    }

    /**
     * Returns if a given String is a valid coverage amount.
     */
    public static boolean isValidCoverageAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns if a given double is a valid coverage amount.
     */
    public static boolean isValidCoverageAmount(double test) {
        if (test < 0) {
            return false;
        }
        double scaled = test * 100;
        return scaled == Math.floor(scaled);
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
        if (!(other instanceof CoverageAmount)) {
            return false;
        }

        CoverageAmount otherCoverageAmount = (CoverageAmount) other;
        return value == otherCoverageAmount.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
