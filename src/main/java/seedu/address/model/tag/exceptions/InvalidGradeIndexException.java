package seedu.address.model.tag.exceptions;

/**
 * Signals that the grade index is invalid.
 */
public class InvalidGradeIndexException extends RuntimeException {
    public InvalidGradeIndexException() {
        super("Invalid grade index has been entered");
    }
}
