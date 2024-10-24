package seedu.address.model.person;

/**
 * Represents a Price of a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Price {
    public static final String MESSAGE_CONSTRAINTS = "Prices should be numeric";
    public static final String VALIDATION_REGEX = "\\d+"; // Checks if the price is numeric
    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        this.value = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns an Integer representation of Price.
     */
    public Integer getPrice() {
        return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(Price price) {
        return Integer.parseInt(this.value) - Integer.parseInt(price.value);
    }
}
