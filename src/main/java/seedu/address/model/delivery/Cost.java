package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;
import java.text.ParseException;

import seedu.address.model.delivery.exceptions.InvalidCostException;

/**
 * Represents a delivery's cost.
 * <p>
 * Guarantees: is valid as declared in {@link #isValidCost(String)}
 */
public class Cost {
    public static final String MESSAGE_CONSTRAINTS =
            "Cost should start with $, followed by positive integer dollar input, and optional 2 decimal cents input.\n"
            + "For example, accepted inputs are: $100, $100.00, $100.10. ";
    public static final String MESSAGE_INVALID_COST_STRING =
            "The cost string within Cost has incorrect format. Something is wrong with the validation of cost input.";
    public static final String VALIDATION_REGEX = "^\\$\\d+(\\.\\d{2})?$"; // This allows optional 2 d.p. cents input.
    public final String value;

    /**
     * Constructs a {@code Cost}.
     *
     * @param cost A valid cost.
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setMinimumFractionDigits(2);
        value = "$" + decimalFormat.format(Double.parseDouble(cost.substring(1)));
    }

    /**
     * Returns the current cost {@code String} as a double value.
     */
    public double asDouble() {
        try {
            DecimalFormat decimalFormat = new DecimalFormat();
            Number parsedCost = decimalFormat.parse(this.value.substring(1));
            return parsedCost.doubleValue();
        } catch (ParseException e) {
            // This should never happen since we have already verified that the cost is valid.
            throw new InvalidCostException(MESSAGE_INVALID_COST_STRING);
        }
    }

    /**
     * Returns true if a given string is a valid cost.
     */
    public static boolean isValidCost(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instance of handles nulls
        if (!(other instanceof Cost)) {
            return false;
        }

        Cost otherCost = (Cost) other;
        return value.equals(otherCost.value);
    }

    @Override
    public String toString() {
        return "Cost: " + value;
    }
}
