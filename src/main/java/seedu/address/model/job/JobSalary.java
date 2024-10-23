package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's salary in the address book.
 */
public class JobSalary {
    public static final String MESSAGE_CONSTRAINTS =
            "Job salary should only contain numbers, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Digit}][\\p{Digit}]*";

    public final int value;

    /**
     * Constructs a {@code JobSalary}.
     *
     * @param salary A valid salary.
     */
    public JobSalary(String salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(salary), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(salary);
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
        return value == otherSalary.value;
    }

    @Override
    public String toString() {
        return "$" + value;
    }
}
