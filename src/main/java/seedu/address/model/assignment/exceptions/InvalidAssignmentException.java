package seedu.address.model.assignment.exceptions;

/**
 * Signals that the operation will result in an invalid assigment.
 */
public class InvalidAssignmentException extends Exception {
    public InvalidAssignmentException(String message) {
        super(message);
    }
}
