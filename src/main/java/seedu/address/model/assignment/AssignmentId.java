package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment's id in the address book.
 * Guarantees: immutable; is always valid
 */
public class AssignmentId {
    public static final String MESSAGE_CONSTRAINTS = "Assignment ID should only contain numeric characters";
    public final String value;

    /**
     * Constructs an {@code AssignmentId}.
     *
     * @param assignmentId An assignment id.
     */
    public AssignmentId(String assignmentId) {
        requireNonNull(assignmentId);
        checkArgument(isValidAssignmentId(assignmentId), MESSAGE_CONSTRAINTS);
        value = assignmentId;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid assignment id.
     */
    public static boolean isValidAssignmentId(String test) {
        try {
            Integer.valueOf(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentId // instanceof handles nulls
                        && value.equals(((AssignmentId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
