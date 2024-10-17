package seedu.address.model.listing;

import java.math.BigDecimal;

public class Price {
    private final String formattedValue;
    private final BigDecimal rawValue;

    // Parse the formatted value into mil, k, etc.
    // e.g. 5 mil, 600k

    public Price(String formattedValue, BigDecimal rawValue) {
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
