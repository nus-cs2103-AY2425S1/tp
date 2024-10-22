package seedu.address.model.person.exceptions;

/**
 * Signals that the student ID entered is of an invalid format.
 */
public class InvalidStudentIdException extends RuntimeException {
    public InvalidStudentIdException(String message) {
        super(message);
    }
}
