package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a delivery's cost.
 * Guarantees: is valid as declared in {@link #isValidCost(String)}
 */
public class Cost {
    public static final String MESSAGE_CONSTRAINTS =
            "Cost should only contain numbers, and it should start with $";
    public static final String VALIDATION_REGEX = "^\\$\\d+$"; // This does not check for decimals (cents).
    public final String value;

    /**
     * Constructs a {@code Cost}.
     *
     * @param cost A valid cost.
     */
    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        value = cost;
    }

    public float asFloat() {
        return Float.parseFloat(this.value.substring(1));
    }

    /**
     * Returns true if a given string is a valid cost.
     */
    public static boolean isValidCost(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Cost: " + value;
    }
}
