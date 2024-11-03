package keycontacts.model.exceptions;

/**
 * Represents an error which occurs due to undo or redo.
 */
public class StateNotFoundException extends Exception {

    public StateNotFoundException(String message) {
        super(message);
    }

}
