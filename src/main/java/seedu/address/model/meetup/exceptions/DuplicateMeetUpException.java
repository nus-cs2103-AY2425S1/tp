package seedu.address.model.meetup.exceptions;

/**
 * Signals that the operation will result in duplicate Buyers (Buyers are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMeetUpException extends RuntimeException {
    public DuplicateMeetUpException() {
        super("Operation would result in duplicate meetups");
    }
}
