package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJobCode(String)}
 */
public class JobCode {

    public static final String MESSAGE_CONSTRAINTS = "Job code should have length of 2 to 12 characters inclusive,"
            + " consisting of only alphanumeric characters and cannot have whitespace.\nFor example: XYZ123";

    /*
     * The first character of the JobCode must not be a whitespace. There is no whitespace
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9]+$";
    public static final int MAX_LENGTH = 12;
    public static final int MIN_LENGTH = 2;

    public static final Comparator<JobCode> JOBCODE_COMPARATOR = Comparator.comparing(jobCode -> jobCode.value);


    public final String value;

    /**
     * Constructs a JobCode object.
     * @param jobCode String provided by user.
     */
    public JobCode(String jobCode) {
        requireNonNull(jobCode);
        jobCode = jobCode.trim();
        checkArgument(isValidJobCode(jobCode), MESSAGE_CONSTRAINTS);
        value = jobCode.trim();

    }

    /**
     * Returns true if a given string is a valid email.
     * Tests against minimum and maximum length, presence of whitespace, and if it is fully alphanumeric.
     */
    public static boolean isValidJobCode(String test) {
        // Check that the string has no whitespace and its length is between 2 and 12
        return test.length() >= MIN_LENGTH
                && test.length() <= MAX_LENGTH
                && !test.contains(" ")
                && test.matches(VALIDATION_REGEX);

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
