package seedu.address.model.assignment;

import seedu.address.model.id.Id;

/**
 * Represents an assignment's id in the address book.
 * Guarantees: immutable; is always valid
 */
public class AssignmentId extends Id {

    /**
     * Constructs an {@code AssignmentId}.
     *
     * @param assignmentId An assignment id.
     */
    public AssignmentId(String assignmentId) {
        super(assignmentId);
    }
}
