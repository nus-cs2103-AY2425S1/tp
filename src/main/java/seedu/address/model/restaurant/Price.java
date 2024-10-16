package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the price the average user expects to spend on a single meal at at
 * vendor. Guarantees: immutable; is valid as declared in
 * {@link #isValidPrice(double)}
 */
public class Price implements Comparable<Price> {

    public static final String MESSAGE_INVALID_PRICE = "Price should"
            + "only be a nonegative number, with two or less decimal points (can also have no decimals)";
    public final double value;

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

    /**
     * Returns a string representation of the price category based on the value of
     * @return
     */
    public String getPriceCategoryString() {
        if (this.value <= 10) {
            return "$";
        } else if (this.value <= 20) {
            return "$$";
        } else if (this.value <= 40) {
            return "$$$";
        } else {
            return "$$$$";
        }
    }

    /**
     * Returns a Price object based on the string value of the priceString.
     * @param priceString the string representation of the price
     * @return a Price object with the parsed value
     * @throws IllegalArgumentException if the input is invalid
     */
    public static Price fromString(String priceString) {
        // Check if the input is null or empty
        if (priceString == null || priceString.isEmpty()) {
            throw new IllegalArgumentException("Price string cannot be null or empty");
        }

        try {
            String cleanedString = priceString.trim();
            double value = Double.parseDouble(cleanedString);
            return new Price(value);
        } catch (NumberFormatException e) {
            // Handle cases where the string cannot be converted to a valid number
            throw new IllegalArgumentException("Invalid price format: " + priceString, e);
        }
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
