package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents a Property's asking price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class AskingPrice {

    public static final String MESSAGE_CONSTRAINTS = "Asking price of property (to the nearest SGD). \n"
            + "It should be a positive integer more than 0 and not be blank. \n"
            + "It can contain commas at the right positions (exactly 3 digits after each comma) \n"
            + "E.g. 10,000 and 10000 are both accepted but 1,0000 is NOT accepted). ";

    public static final String VALIDATION_REGEX = "^0*([1-9]\\d*|[1-9]\\d{0,2}(,\\d{3})*)$";

    public final String value;

    /**
     * Constructs a {@code AskingPrice}.
     *
     * @param askingPrice A valid asking price.
     */
    public AskingPrice(String askingPrice) {
        requireNonNull(askingPrice);
        checkArgument(isValidPrice(askingPrice), MESSAGE_CONSTRAINTS);
        value = formatNumber(askingPrice);
    }

    /**
     * Returns true if a given string is a valid asking price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static String formatNumber(String input) {
        // Remove any existing commas
        String numberWithoutCommas = input.replaceAll(",", "");

        // Parse the string as a long to handle larger numbers
        long number = Long.parseLong(numberWithoutCommas);

        // Format the number with commas
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(number);
    }

    /**
     * Returns pretty formatted String
     */
    public String toPrettyString() {
        return String.format("$%s", value);
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
        if (!(other instanceof AskingPrice)) {
            return false;
        }

        AskingPrice otherPrice = (AskingPrice) other;
        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
