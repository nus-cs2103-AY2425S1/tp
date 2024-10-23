package seedu.address.model.person;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Cannot be negative
 * Cannot be more than 2dp
 *
 */

/**
 * Represents a Vendor's budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudget(Double)}
 */
public class Budget {
    public static final String MESSAGE_CONSTRAINTS =
            "Budget should be a non-negative number with up to 2 decimal places. Cannot have more than 15 significant digits.";
    public static final Double MINIMUM_BUDGET = 0.00;
    public static final String FORMAT_2DP = "%.2f";

    public final Double value;

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget amoount.
     */
    public Budget(Double budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        value = budget;
    }

    /**
     * Returns true if the given double value is a valid budget.
     */
    public static boolean isValidBudget(Double test) {
        // Check negative
        if (test < MINIMUM_BUDGET) {
            return false;
        }

        // Check 2dp or less
        BigDecimal budgetDecimal = BigDecimal.valueOf(test);
        int scale = budgetDecimal.scale();
        return scale <= 2;
    }

    @Override
    public String toString() {
        return String.format(FORMAT_2DP, value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return value.equals(otherBudget.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
