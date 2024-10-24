package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a property postal code in the address book.
 * Guarantees: immutable; postal code is valid as declared in {@link #isValidPostalCode(String)}.
 */
public class PostalCode {
    private static final Logger logger = LogsCenter.getLogger(PostalCode.class);
    public static final String MESSAGE_CONSTRAINTS =
            "Postal Code numbers should only contain positive numbers, and it should be exactly 6 digits long";
    public static final String VALIDATION_REGEX = "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code PostalCode}.
     *
     * @param postalCode A valid postal code.
     */
    public PostalCode(String postalCode) {
        logger.info("Creating PostalCode object: " + postalCode);
        requireNonNull(postalCode);
        assert postalCode != null : "PostalCode string cannot be null";
        checkArgument(isValidPostalCode(postalCode), MESSAGE_CONSTRAINTS);
        assert isValidPostalCode(postalCode) != false : "Postal Code string must be 6 digit non-negative integer";
        logger.info("Postal Code object created: " + postalCode);
        value = postalCode;
    }

    /**
     * Returns true if a given string is a valid postal code.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof PostalCode)) {
            return false;
        }

        PostalCode otherPostalCode = (PostalCode) other;
        return value.equals(otherPostalCode.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
