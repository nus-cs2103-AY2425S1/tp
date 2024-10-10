package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Cost {
    public static final String MESSAGE_CONSTRAINTS =
            "Cost should only contain numbers, and it should start with $";
    public static final String VALIDATION_REGEX = "^\\$\\d+$";
    public final String value;

    public Cost(String cost) {
        requireNonNull(cost);
        checkArgument(isValidCost(cost), MESSAGE_CONSTRAINTS);
        value = cost;
    }

    public static boolean isValidCost(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
