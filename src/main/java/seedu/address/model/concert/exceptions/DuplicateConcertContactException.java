package seedu.address.model.concert.exceptions;

/**
 * Signals that the operation will result in duplicate ConcertContacts (ConcertContacts are considered duplicates
 * if they have the same identity)
 */
public class DuplicateConcertContactException extends RuntimeException {
    public DuplicateConcertContactException() {
        super("Operation would result in duplicate concert-contacts");
    }
}
