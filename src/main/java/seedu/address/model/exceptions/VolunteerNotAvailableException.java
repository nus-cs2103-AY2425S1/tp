package seedu.address.model.exceptions;

/**
 * Signals that a volunteer is not available on the day of the provided event.
 */
public class VolunteerNotAvailableException extends RuntimeException {
    /**
     * Constructor for this exception
     * @param eventName
     */
    public VolunteerNotAvailableException(String eventName) {
        super(String.format("The volunteer is not available to attend %s. \nPlease check if they are"
                + " available that day.", eventName));
    }
}
