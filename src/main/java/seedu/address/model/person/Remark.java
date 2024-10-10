package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remarks in the address book, as added by a user.
 * Guarantees: immutable;
 */

public class Remark {

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A remark, in the form of a string.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        } else if (!(otherObj instanceof Remark)) {
            return false;
        }
        Remark typeCastedOtherObj = (Remark) otherObj;
        return value.equals((typeCastedOtherObj.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
