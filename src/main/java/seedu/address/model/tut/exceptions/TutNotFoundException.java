package seedu.address.model.tut.exceptions;

/**
 * Signals that the operation is unable to find the specified Tutorial.
 */
public class TutNotFoundException extends RuntimeException {

    public TutNotFoundException() {
        super("Tutorial doesn't exist!");
    }
}
