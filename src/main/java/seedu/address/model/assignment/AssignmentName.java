package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Assignment's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class AssignmentName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final int MAXIMUM_NAME_LENGTH = 55;
    public static final String MESSAGE_NAME_TOO_LONG = "The assignment name should not "
            + "exceed the maximum length of %d characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String assignmentName;

    /**
     * Constructs a {@code Name}.
     *
     * @param assignmentName A valid name.
     */
    public AssignmentName(String assignmentName) {
        requireNonNull(assignmentName);
        checkArgument(isValidName(assignmentName), MESSAGE_CONSTRAINTS);
        this.assignmentName = assignmentName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return assignmentName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignmentName)) {
            return false;
        }

        AssignmentName otherName = (AssignmentName) other;
        return assignmentName.equals(otherName.assignmentName);
    }

    @Override
    public int hashCode() {
        return assignmentName.hashCode();
    }

}
