package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's monthly salary in the address book.
 */
public class JobSalary {

    public static final String MESSAGE_CONSTRAINTS = "Monthly job salary should only contain numeric characters, "
            + "and it should not be blank\n"
            + "It is also limited to a value smaller than 2147483648";

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
        requireNonNull(test);
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
