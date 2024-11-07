package seedu.address.model.exceptions;

/**
 * Signals that a volunteer remove date command will remove all of their dates
 * and prevent them from being properly saved and reloaded.
 */
public class VolunteerNotAvailableOnAnyDayException extends RuntimeException {
    /**
     * Constructor for this exception
     */
    public VolunteerNotAvailableOnAnyDayException() {
        super();
    }
}
