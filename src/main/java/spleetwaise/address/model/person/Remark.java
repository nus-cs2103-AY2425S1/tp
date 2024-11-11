package spleetwaise.address.model.person;

import static java.util.Objects.requireNonNull;

import spleetwaise.commons.util.AppUtil;

/**
 * Represents a Person's remark in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidRemark(String)}
 */
public class Remark {
    public static final int MAX_LENGTH = 120;
    public static final String MESSAGE_CONSTRAINTS = "Remarks should not be blank or more than " + MAX_LENGTH
            + " characters.";
    public final String value;

    /**
     * Constructor for remark
     */
    public Remark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        AppUtil.checkArgument(isValidRemark(trimmedRemark), MESSAGE_CONSTRAINTS);
        value = remark.trim();
    }

    public static boolean isValidRemark(String test) {
        return test.trim().length() <= MAX_LENGTH;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
