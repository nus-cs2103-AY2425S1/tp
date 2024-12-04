package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Listing's address in the real estate app.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}.
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";


    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A non-empty address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address.trim();
    }

    /**
     * Returns true if a given string is a valid address.
     *
     * An address is valid if the string is non-empty.
     */
    public static boolean isValidAddress(String test) {
        return !test.trim().isEmpty();
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
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
