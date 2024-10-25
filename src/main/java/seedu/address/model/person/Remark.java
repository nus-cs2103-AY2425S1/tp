package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a remark about a person in the addressbook.
 */
public class Remark {

    private static final String MESSAGE_CONSTRAINTS = "Remarks should be between 0 and 200 characters long.";
    public static final String VALIDATION_REGEX = "^.{0,200}$";

    public final String value;

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
