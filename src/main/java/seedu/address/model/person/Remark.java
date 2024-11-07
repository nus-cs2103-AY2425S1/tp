package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark about a person in the addressbook.
 */
public class Remark {

    public static final String VALIDATION_REGEX = "^.{1,200}$";
    public static final String MESSAGE_CONSTRAINTS = "Remarks should be between 1 and 200 characters long.\n "
            + "It cannot be blank";

    public final String value;

    /**
     * Constructs a {@code remark}
     *
     * @param remark a remark between 0 and 200 characters
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Remark
                && value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
