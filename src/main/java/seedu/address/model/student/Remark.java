package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Remark in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidRemarkName(String)}
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remarks names should be ";
    public static final String VALIDATION_REGEX = "\\p{Print}*";

    public final String remarkName;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remarkName A valid remark name.
     */
    public Remark(String remarkName) {
        requireNonNull(remarkName);
        checkArgument(isValidRemarkName(remarkName), MESSAGE_CONSTRAINTS);
        this.remarkName = remarkName;
    }

    /**
     * Returns true if a given string is a valid remark name.
     */
    public static boolean isValidRemarkName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return remarkName.equals(otherRemark.remarkName);
    }

    @Override
    public int hashCode() {
        return remarkName.hashCode();
    }

}
