package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJobCode(String)}
 */
public class JobCode {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid job code: only alphanumeric characters and single dash (-) are allowed. \n"
                    + "The first and last character must be alphanumeric. "
                    + "Two consecutive dashes are not allowed. \n"
                    + "Example: SWE2023, HR-2023-intern";

    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "Job code cannot have more than 50 characters.";

    /**
     * The input string must follow these rules:
     * - Only allows alphanumeric characters (A-Z, a-z, 0-9) and single dashes ("-").
     * - The first character must be alphanumeric or a dash.
     * - There must not be two consecutive dashes ("--").
     */
    public static final String VALIDATION_REGEX = "^(?!.*--)[A-Za-z0-9]([-]?[A-Za-z0-9])*$";
    public static final int MAX_LENGTH = 50;

    public static final Comparator<JobCode> JOBCODE_COMPARATOR = Comparator
            .comparing(jobCode -> jobCode.value);


    public final String value;

    /**
     * Constructs a JobCode object.
     * @param jobCode String provided by user.
     */
    public JobCode(String jobCode) {
        requireNonNull(jobCode);
        jobCode = jobCode.trim().replaceAll("\\s", "");

        checkArgument(isValidJobCode(jobCode), MESSAGE_CONSTRAINTS);
        checkArgument(isValidLengthJobCode(jobCode), MESSAGE_LENGTH_CONSTRAINTS);
        value = jobCode.toUpperCase();
    }


    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidJobCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string have a valid length.
     */
    public static boolean isValidLengthJobCode(String test) {
        return test.length() <= MAX_LENGTH;
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
