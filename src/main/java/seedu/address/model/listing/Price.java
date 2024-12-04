package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

/**
 * Represents the price of a listing in the real estate application.
 * The price is stored both as a formatted string (e.g., "5 mil", "600k") and as a raw {@code BigDecimal} value.
 */
public class Price {
    public static final String MESSAGE_CONSTRAINTS =
            "Price should only contain positive numbers and cannot start with zeroes, "
                    + "and it should be at least 6 digits long";
    private static final String VALIDATION_REGEX = "^[1-9]\\d{5,}$";

    private final String formattedValue;
    private final BigDecimal rawValue;

    /**
     * Constructs a {@code Price}.
     *
     * @param formattedValue The formatted price value, such as "5 mil" or "600k".
     * @param rawValue       The raw price value, represented as a {@code BigDecimal}.
     */
    public Price(String formattedValue, BigDecimal rawValue) {
        requireNonNull(formattedValue);
        this.formattedValue = formattedValue;
        this.rawValue = rawValue;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public BigDecimal getRawValue() {
        return this.rawValue;
    }

    public String getFormattedValue() {
        return this.formattedValue;
    }

    @Override
    public String toString() {
        return this.formattedValue;
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
        return formattedValue.equals(otherPrice.formattedValue);
    }

    @Override
    public int hashCode() {
        return formattedValue.hashCode();
    }
}
