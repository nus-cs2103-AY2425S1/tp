package seedu.sellsavvy.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.AppUtil.checkArgument;

/**
 * Represents the item quantity of a Person's Order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Item quantity should be a positive integer";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";
    public final String value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid item quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid quantity number.
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

        // instanceof handles nulls
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

}
