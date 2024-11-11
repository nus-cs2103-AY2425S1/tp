package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {
    public final String value;

    /**
     * Constructs a Remark class with a String value
     * representing the remark.
     * @param remark
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
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

        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;

        return this.value.equals(otherRemark.value);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
