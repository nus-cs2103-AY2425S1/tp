package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery's cost in the delivery.
 */
public class Cost implements Comparable<Cost> {

    public static final String MESSAGE_CONSTRAINTS =
            "COST should be a positive number with up to two decimal places and must not be blank.";

    // Regex asserts that the decimal portion is optional but it is limited to 2 places --> so both 100 and 100.00
    // are valid
    public static final String VALIDATION_REGEX = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final String value;

    /**
     * Constructs a {@code Cost}.
     *
     * @param cost A valid cost string.
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        value = cost;
    }

    /**
     * Returns true if the given string is a valid cost.
     */
    public static boolean isValidCost(String test) {
        boolean isNumber = test.matches(VALIDATION_REGEX);
        boolean isValid = isNumber && !(Double.parseDouble(test) == 0);
        return isValid;
    }

    /**
     * Compares this cost with the specified cost for order.
     * Returns a negative integer, zero, or a positive integer as this cost is less than, equal to,
     * or greater than the specified cost.
     *
     * @param otherCost The cost to be compared.
     * @return A negative integer, zero, or a positive integer as this cost is less than, equal to,
     *         or greater than the other cost.
     */
    public int compareTo(Cost otherCost) {
        double deliveryCost = Double.parseDouble(value);
        double otherDeliveryCost = Double.parseDouble(otherCost.value);
        return Double.compare(deliveryCost, otherDeliveryCost);
    }

    @Override
    public String toString() {
        return value;
    }

    public String displayString() {
        return "$" + value;
    }

    public String getCost() {

        assert value != null;
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cost)) {
            return false;
        }

        Cost otherCost = (Cost) other;
        return Double.valueOf(value).equals(Double.valueOf(otherCost.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
