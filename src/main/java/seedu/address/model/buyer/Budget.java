package seedu.address.model.buyer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents a Buyer's budget in the buyer list.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudget(String)}
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS = "Budget of the buyer (to the nearest SGD). "
            + "It should be a positive integer more than 0 and not be blank. \n"
            + "It can contain commas at the right positions (exactly 3 digits after each comma) \n"
            + "E.g. 10,000 and 10000 are both accepted but 1,0000 is NOT accepted). ";

    /*
     * Budget must be an integer greater than 0.
     * It can contain commas in the correct position e.g. 1,000 and 10,000.
     * It can have leading or trailing zeros and cannot be blank.
     */
    public static final String VALIDATION_REGEX = "^0*([1-9]\\d*|[1-9]\\d{0,2}(,\\d{3})*)$";

    public final String value;

    /**
     * Constructs an {@code Budget}. Budget will automatically be rounded to nearest whole number
     *
     * @param budget A valid budget.
     */
    public Budget(String budget) {
        requireNonNull(budget);
        checkArgument(isValidBudget(budget), MESSAGE_CONSTRAINTS);
        value = formatNumber(budget);
    }

    /**
         * Returns true if a given string is a valid budget.
     */
    public static boolean isValidBudget(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static String formatNumber(String input) {
        // Remove any existing commas
        String numberWithoutCommas = input.replaceAll(",", "");

        // Parse the string as a long to handle larger numbers
        long number = Long.parseLong(numberWithoutCommas);

        // Format the number with commas
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(number);
    }

    @Override
    public String toString() {
        return String.format("$%s",value);
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
