package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents bid price (maximum buying price) of property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bid {
    private static final Logger logger = LogsCenter.getLogger(Bid.class);
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
        logger.info("Creating Bid object: " + bid);
        requireNonNull(bid);
        assert bid != null : "Bid string cannot be null";
        checkArgument(isValidBid(bid), MESSAGE_CONSTRAINTS);
        assert isValidBid(bid) != false : "Bid string must be non-negative integer";
        logger.info("Bid object created: " + bid);
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
