package seedu.address.model.tut.exceptions;

/**
 * Signals that the operation will result in duplicate tutorials (tutorials are considered duplicates if they
 * have the same tutorial id).
 */
public class DuplicateTutorialException extends RuntimeException {
    public DuplicateTutorialException() {
        super("Operation would result in duplicate tutorial");
    }

}
