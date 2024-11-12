package tuteez.model.remark;

import static java.util.Objects.requireNonNull;
import static tuteez.commons.util.AppUtil.checkArgument;

/**
 * Represents a Remark in the address book.
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remarks should not be blank";

    private static final String VALIDATION_REGEX = ".+";

    private final String remark;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        this.remark = remark;
    }

    /**
     * Returns true if a given string is a valid telegram handle.
     *
     * @param test the string to test
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return remark;
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
        return remark.equals(otherRemark.remark);
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
