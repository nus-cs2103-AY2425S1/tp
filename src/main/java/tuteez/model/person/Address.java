package tuteez.model.person;

import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank"
            + ", unless when editing.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        // address argument can be null to make it optional
        if (address != null) {
            requireNonNull(address);
            checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        }
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        if (test == null) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
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
        if (value == null) {
            return otherAddress.value == null;
        }
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

}
