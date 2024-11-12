package seedu.address.model.tut.exceptions;

/**
 * Signals that the operation is unable to find the specified Tutorial Date.
 */
public class TutDateNotFoundException extends RuntimeException {
    public TutDateNotFoundException() {
        super("No tutorial session exists for the specified date.");
    }
}
