package spleetwaise.address.model.person;

import static java.util.Objects.requireNonNull;

import spleetwaise.address.commons.util.AppUtil;

/**
 * Represents a Person's remark in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidRemark(String)}
 */
public class Remark {
    // Regex pattern to validate that the remark contains only alphanumeric characters and spaces, and does not start
    // with a space. The regex match a string that starts with an alphanumeric character and is followed by zero or
    // more alphanumeric characters or spaces
    public static final String VALIDATION_REGEX = "^[\\p{L}\\p{N}\\p{P}\\p{S}\\p{Zs}\\t]*$";
    public static final String MESSAGE_CONSTRAINTS = "Remarks should only contain alphanumeric characters and spaces";
    public final String value;

    /**
     * Constructor for remark
     */
    public Remark(String remark) {
        requireNonNull(remark);
        AppUtil.checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
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
