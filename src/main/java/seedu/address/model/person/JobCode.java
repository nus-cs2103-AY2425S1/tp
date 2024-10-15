package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class JobCode {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public JobCode(String jobCode) {
        requireNonNull(jobCode);
        checkArgument(isValidJobCode(jobCode), MESSAGE_CONSTRAINTS);
        value = jobCode;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidJobCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobCode)) {
            return false;
        }

        JobCode otherJobCode = (JobCode) other;
        return value.equals(otherJobCode.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
