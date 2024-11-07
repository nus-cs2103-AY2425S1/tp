package seedu.address.model.client;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandCommons;


/**
 * Represents a Client's remarks in the address book, as added by a user.
 * Guarantees: immutable;
 */

public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remark must contain only ASCII characters.";
    public static final String VALIDATION_REGEX = "^[\\x00-\\x7F]+$";

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

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
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

    /**
     * Constructs an {@code Remark}, with the concatenated values of all give {@code Remark remarks}.
     *
     * @param remarks Multiple remarks.
     */
    public static Remark combineRemarks(Remark... remarks) {
        StringBuilder combinedValues = new StringBuilder();
        for (Remark currentRemark: remarks) {
            if (!currentRemark.value.equalsIgnoreCase(CommandCommons.DEFAULT_REMARK)) {
                combinedValues.append(currentRemark.value).append("\n");
            }
        }
        return new Remark(combinedValues.toString().trim());
    }

}
