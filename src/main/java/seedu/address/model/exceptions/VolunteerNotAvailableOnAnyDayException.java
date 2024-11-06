package seedu.address.model.exceptions;

/**
 * Signals that a volunteer remove date command will remove all of their dates
 * and prevent them from being properly saved and reloaded.
 */
public class VolunteerNotAvailableOnAnyDayException extends RuntimeException {
    /**
     * Constructor for this exception
     * @param eventName
     */
    public VolunteerNotAvailableOnAnyDayException() {
        super("Volunteers must be free on at least 1 day! This remove command will leave them with no available days."
                + "\nPlease add at least 1 more day.");
    }
}
