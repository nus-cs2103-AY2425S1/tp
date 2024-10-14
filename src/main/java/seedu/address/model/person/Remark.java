package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remarks in the address book, as added by a user.
 * Guarantees: immutable;
 */

public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remarks cannot be empty. They must at least consist of 1 "
            + "character";

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

    public static boolean isValidRemark(String test) {
        return test != null && !(test.isEmpty());
    }

    /**
     * Constructs an {@code Remark}, with the concatenated values of all give {@code Remark remarks}.
     *
     * @param remarks Multiple remarks.
     */
    public static Remark combineRemarks(Remark... remarks) {
        StringBuilder combinedValues = new StringBuilder();
        for (Remark currentRemark: remarks) {
            combinedValues.append(currentRemark.value + "\n");
        }
        return new Remark(combinedValues.toString());
    }

}
