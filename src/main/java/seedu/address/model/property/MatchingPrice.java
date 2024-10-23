package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents matching price of property in the property book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MatchingPrice {
    public static final String MESSAGE_CONSTRAINTS =
            "Matching price should be an integer";
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code MatchingPrice}.
     *
     * @param matchingPrice A valid Matching Price.
     */
    public MatchingPrice(String matchingPrice) {
        requireNonNull(matchingPrice);
        checkArgument(isValidMatchingPrice(matchingPrice), MESSAGE_CONSTRAINTS);
        value = matchingPrice;
    }

    /**
     * Returns the intersection of ask and bid
     */
    public static int getMatchingPrice(Ask ask, Bid bid) {
        return (ask.toInteger() + bid.toInteger()) / 2;
    }

    /**
     * Returns true if a given string is a valid Matching Price.
     */
    public static boolean isValidMatchingPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    public int toInteger() {
        return Integer.parseInt(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchingPrice)) {
            return false;
        }

        MatchingPrice otherMatchingPrice = (MatchingPrice) other;
        return value.equals(otherMatchingPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
