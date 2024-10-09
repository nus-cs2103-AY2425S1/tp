package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the price the average user expects to spend on a single meal at at vendor.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(double)}
 */
public class Price implements Comparable<Price> {

    public final double value;
    public static final String MESSAGE_INVALID_PRICE =
            "Price should only be a nonegative number, with two or less decimal points (can also have no decimals)";

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(double price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_INVALID_PRICE);
        value = price;
    }

    /**
     * Returns true if a given double is a valid price.
     */
    public static boolean isValidPrice(double price) {
        return price >= 0 && price == Math.round(price * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
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
        return value == otherPrice.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public int compareTo(Price other) {
        return Double.compare(value, other.value);
    }
}

