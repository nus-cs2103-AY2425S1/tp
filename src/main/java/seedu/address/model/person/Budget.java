package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Vendor's budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudget(String)}
 */
public class Budget {
    public static final String MESSAGE_CONSTRAINTS =
            "Budget should be a non-negative number with up to 2 decimal places. ";
    public static final String VALIDATION_REGEX = "\\d*(\\.\\d{1,2})?";
    public final String value;

    /**
     * Constructs a {@code Budget}.
     *
     * @param budget A valid budget amoount.
     */
    public Budget(String budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);

        if (!budget.contains(".")) {
            budget += ".00";
        }

        String[] parts = budget.split("\\.");
        if (parts[0].isEmpty()) {
            budget = "0" + budget;
        }
        if (parts[1].length() == 1) {
            budget += "0";
        }
        value = budget;
    }

    /**
     * Returns true if the given double value is a valid budget.
     */
    public static boolean isValidBudget(String test) {
        return !test.isEmpty() && test.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return value.equals(otherBudget.value);
    }
}
