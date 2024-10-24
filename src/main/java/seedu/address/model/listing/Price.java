package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

/**
 * Represents the price of a listing in the real estate application.
 * The price is stored both as a formatted string (e.g., "5 mil", "600k") and as a raw {@code BigDecimal} value.
 */
public class Price {
    private final String formattedValue;
    private final BigDecimal rawValue;


    //Future Extension:
    //Parse the formatted value into mil, k, etc.
    //e.g. 5 mil, 600k

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
