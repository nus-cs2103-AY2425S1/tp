package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's description in the address book.
 */
public class JobDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Job description should not be blank";

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\S.*";

    public final String value;

    /**
     * Constructs a {@code JobDescription}.
     *
     * @param description The details of the job.
     */
    public JobDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
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
        if (!(other instanceof JobDescription)) {
            return false;
        }

        JobDescription otherDescription = (JobDescription) other;
        return value.equals(otherDescription.value);
    }

    @Override
    public String toString() {
        return value;
    }

}
