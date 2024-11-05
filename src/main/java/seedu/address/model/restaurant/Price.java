package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Price tag in the address book. Guarantees: immutable; is valid
 * as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS = "Price categories should be one of the following: "
            + "$, $$, $$$, $$$$";
    public static final String VALIDATION_REGEX = "^\\${1,4}$";
    public final PriceCategory value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = PriceCategory.getCategory(price);
    }

    /**
     * Returns true if a given string is a valid price.
     *
     * @param test the string to test
     * @return true if the given string is a valid price
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the price is represented by the given string.
     *
     * @param test the string to test
     * @return true if the price is represented by the given string
     */
    public boolean isRepresentedBy(String test) {
        return value.toString().equals(test);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Price)) {
            return false;
        }

        Price otherPrice = (Price) other;
        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
