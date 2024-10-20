package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents bid price (maximum buying price) of property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bid {
    public static final String MESSAGE_CONSTRAINTS =
            "Bid price should be an integer";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code Bid}.
     *
     * @param bid A valid bid.
     */
    public Bid(String bid) {
        requireNonNull(bid);
        checkArgument(isValidBid(bid), MESSAGE_CONSTRAINTS);
        value = bid;
    }

    /**
     * Returns true if a given string is a valid bid.
     */
    public static boolean isValidBid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    public int toInteger() {
        return Integer.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Bid)) {
            return false;
        }

        Bid otherBid = (Bid) other;
        return value.equals(otherBid.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
