package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to add the meeting due to clash in timings.
 */
public class TimeClashException extends RuntimeException {

    public TimeClashException() {
        super("Operation would result in time clash");
    }
}
