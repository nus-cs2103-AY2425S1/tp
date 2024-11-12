package seedu.address.model.exceptions;

/**
 * Signals that a volunteer is already marked as available on the given date.
 */
public class VolunteerDuplicateDateException extends RuntimeException {
    public VolunteerDuplicateDateException(String d) {
        super(String.format("The volunteer cannot be free on %s twice!", d));
    }
}
