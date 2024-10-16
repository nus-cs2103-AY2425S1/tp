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
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && remark.equals(((Remark) other).remark)); // state check
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
