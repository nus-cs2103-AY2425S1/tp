package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery's quantity in the delivery book.
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS =
            "QUANTITY should be a positive number followed by a space and a valid unit (e.g. 100 kg, 150 L). "
                    + "Valid units are: kg, g, L, mL, units";

    public static final String VALIDATION_REGEX = "^([1-9]\\d*)(\\s+)(kg|g|L|mL|units)$";

    public final String value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity with the correct units.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);

        // Check validity before performing the formatting
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantityFormatter(quantity);
    }

    /**
     * Checks that input is a number bigger than 0 and contains units
     *
     * @param test Input quantity provided by user.
     * @return True if test contains a number bigger than 0 followed by units.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
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

        if (!(other instanceof Quantity)) {
            return false;
        }

        Quantity otherQuantity = (Quantity) other;
        return value.equals(otherQuantity.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Trims leading and trailing spaces from the input string and ensures there is exactly one space
     * between the numeric part and the unit
     *
     * @param quantity A string representing the quantity with a number and a unit.
     * @return A formatted string with exactly one space between the number and the unit.
     */
    private String quantityFormatter(String quantity) {
        String[] parts = quantity.trim().split("\\s+");
        return parts[0].trim() + " " + parts[1].trim();
    }
}
