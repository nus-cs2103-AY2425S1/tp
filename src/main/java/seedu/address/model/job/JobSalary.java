package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's salary in the address book.
 */
public class JobSalary {
    public static final String MESSAGE_CONSTRAINTS =
            "Job salary should only contain numeric characters, be more than zero, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[1-9][\\p{Digit}]*";

    public final String value;

    /**
     * Constructs a {@code JobSalary}.
     *
     * @param salary A valid salary.
     */
    public JobSalary(String salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(salary), MESSAGE_CONSTRAINTS);
        value = salary;
    }

    /**
     * Returns true if a given string is a valid salary.
     */
    public static boolean isValidSalary(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobSalary)) {
            return false;
        }

        JobSalary otherSalary = (JobSalary) other;
        return value.equals(otherSalary.value);
    }

    @Override
    public String toString() {
        return "$" + value;
    }
}
