package tuteez.model.remark;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Remark in the address book.
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any remarks, and it should not be blank";

    private final String remark;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.remark = remark;
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
