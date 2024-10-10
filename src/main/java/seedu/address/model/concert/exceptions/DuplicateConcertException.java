package seedu.address.model.concert.exceptions;

/**
 * Signals that the operation will result in duplicate Connerts (Concerts are considered duplicates
 * if they have the same identity)
 */
public class DuplicateConcertException extends RuntimeException {
    public DuplicateConcertException() {
        super("Operation would result in duplicate concerts");
    }
}
