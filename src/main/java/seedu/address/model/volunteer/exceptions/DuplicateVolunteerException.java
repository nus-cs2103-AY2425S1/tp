package seedu.address.model.volunteer.exceptions;

/**
 * Signals that the operation will result in duplicate Volunteers (Volunteers are considered duplicates if they have the same
 * identity).
 */
public class DuplicateVolunteerException extends RuntimeException {
    public DuplicateVolunteerException() {
        super("Operation would result in duplicate volunteers");
    }
}
