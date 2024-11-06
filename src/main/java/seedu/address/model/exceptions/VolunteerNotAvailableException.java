package seedu.address.model.exceptions;

/**
 * Signals that a volunteer is already marked as available on the given date.
 */
public class VolunteerNotAvailableException extends RuntimeException {
    public VolunteerNotAvailableException(String eventName) {
        super(String.format("The volunteer is not available to attend %s. \nPlease check if they are"
                + " available that day.", eventName));
    }
}