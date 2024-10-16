package seedu.address.model.tut.exceptions;

/**
 * Signals that the operation is unable to find the specified Tutorial.
 */
public class TutNoFoundException extends RuntimeException {

    public TutNoFoundException() {
        super("Tutorial doesn't exist!");
    }
}
