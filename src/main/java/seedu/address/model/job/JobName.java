package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's name in the address book.
 */
public class JobName {
    public static final String MESSAGE_CONSTRAINTS =
            "Job names should only contain alphanumeric characters, symbols and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Graph}][\\p{Graph} ]*";

    public final String value;

    /**
     * Constructs a {@code JobName}.
     *
     * @param name A valid name.
     */
    public JobName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        value = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobName)) {
            return false;
        }

        JobName otherName = (JobName) other;
        return value.equals(otherName.value);
    }

    @Override
    public String toString() {
        return value;
    }

}
