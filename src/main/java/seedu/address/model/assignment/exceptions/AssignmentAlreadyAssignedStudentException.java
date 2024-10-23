package seedu.address.model.assignment.exceptions;

/**
 * Signals that the assignment is already assigned to a student.
 */
public class AssignmentAlreadyAssignedStudentException extends RuntimeException {
    public AssignmentAlreadyAssignedStudentException() {
        super("This assignment is already assigned to a student");
    }
}
