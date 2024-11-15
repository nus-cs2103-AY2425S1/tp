package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's premium amount per month.
 * Guarantees: immutable; is valid as declared in {@link #isValidPremiumAmount(String)}
 * or {@link #isValidPremiumAmount(double)}
 */
public class PremiumAmount {
    public static final String MESSAGE_CONSTRAINTS = "Premium amount must be a non-negative numeral with at "
            + "most 2 decimal points.";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public final double value;

    /**
     * Constructs a {@code PremiumAmount} using a {@code String} object.
     *
     * @param premiumAmount The premiumAmount expressed as a String.
     */
    public PremiumAmount(String premiumAmount) {
        requireNonNull(premiumAmount);
        checkArgument(isValidPremiumAmount(premiumAmount), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(premiumAmount);
    }

    /**
     * Constructs a {@code PremiumAmount} using a double.
     *
     * @param premiumAmount The premiumAmount expressed as a double.
     */
    public PremiumAmount(double premiumAmount) {
        checkArgument(isValidPremiumAmount(premiumAmount), MESSAGE_CONSTRAINTS);
        value = premiumAmount;
    }

    /**
     * Returns if a given string is a valid premium amount.
     */
    public static boolean isValidPremiumAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns if a given double is a valid premium amount.
     */
    public static boolean isValidPremiumAmount(double test) {
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
        if (!(other instanceof PremiumAmount)) {
            return false;
        }

        PremiumAmount otherPremiumAmount = (PremiumAmount) other;
        return value == otherPremiumAmount.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
