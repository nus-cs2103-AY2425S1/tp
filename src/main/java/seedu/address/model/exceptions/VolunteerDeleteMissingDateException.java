package seedu.address.model.exceptions;

/**
 * Signals that a volunteer is already marked as available on the given date.
 */
public class VolunteerDeleteMissingDateException extends RuntimeException {
    public VolunteerDeleteMissingDateException(String d) {
        super(String.format("The volunteer is already not free on %s", d));
    }
}
