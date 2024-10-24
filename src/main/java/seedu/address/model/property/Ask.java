package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents ask price (minimum selling price) of property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ask {
    public static final String MESSAGE_CONSTRAINTS =
            "Ask price should be an integer";
    public static final String VALIDATION_REGEX = "\\d+";
    private static final Logger logger = LogsCenter.getLogger(Ask.class);
    public final String value;

    /**
     * Constructs a {@code Ask}.
     *
     * @param ask A valid ask.
     */
    public Ask(String ask) {
        logger.info("Creating Ask object: " + ask);
        requireNonNull(ask);
        assert ask != null : "Ask string cannot be null";
        checkArgument(isValidAsk(ask), MESSAGE_CONSTRAINTS);
        assert isValidAsk(ask) != false : "Ask string must be non-negative integer";
        logger.info("Ask object created: " + ask);
        value = ask;
    }

    /**
     * Returns true if a given string is a valid ask.
     */
    public static boolean isValidAsk(String test) {
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
        if (!(other instanceof Ask)) {
            return false;
        }

        Ask otherAsk = (Ask) other;
        return value.equals(otherAsk.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
