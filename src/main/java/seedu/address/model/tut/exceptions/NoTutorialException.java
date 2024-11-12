package seedu.address.model.tut.exceptions;

/**
 * Signals that the operation cannot proceed because there is no tutorial available.
 * This exception is thrown when an operation specific to a tutorial is attempted
 * on a non-existent or "None" tutorial.
 */
public class NoTutorialException extends RuntimeException {

    /**
     * Constructs a new NoTutorialException with a default error message.
     */
    public NoTutorialException() {
        super("There is no tutorial!");
    }
}
