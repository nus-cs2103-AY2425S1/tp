package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

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
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Normalizes the address by removing all non-alphanumeric characters
     * (such as spaces, commas, and hyphens) and converting all letters to lowercase.
     *
     * For example:
     * - "311, Clementi Ave 5, unit 02-2" will become "311clementiave5unit022"
     * - "123 Main St - Apt 4B" will become "123mainstapt4b"
     *
     * @return A normalized version of the address as a lowercase alphanumeric string.
     */
    public String normaliseAddress() {
        // The regular expression was produced by chatgpt to only include letters upper case and lower case and numbers
        return value.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
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
        return this.normaliseAddress().equals(otherAddress.normaliseAddress());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
