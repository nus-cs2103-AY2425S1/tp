package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid as declared in {@link #Remark(String)}.
 */
public class Remark {

    /** The remark string of the person. */
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark string.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Returns the remark string.
     *
     * @return the string value of the remark.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this remark to the specified object. The result is true if and only if the argument
     * is not null and is a {@code Remark} object that contains the same string value as this remark.
     *
     * @param other The object to compare this {@code Remark} against.
     * @return true if the given object represents a {@code Remark} equivalent to this remark, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    /**
     * Returns the hash code for this remark.
     *
     * @return The hash code value for this remark.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
