package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's postal code number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {


    public static final String MESSAGE_CONSTRAINTS =
            "Postal Code should be exactly 6 digits";
    public static final String VALIDATION_REGEX = "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code PostalCode}.
     *
     * @param postalCode A valid postal code number.
     */
    public PostalCode(String postalCode) {
        requireNonNull(postalCode);
        checkArgument(isValidPostalCode(postalCode), MESSAGE_CONSTRAINTS);
        value = postalCode;
    }

    /**
     * Returns true if a given string is a valid PostalCode number.
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
