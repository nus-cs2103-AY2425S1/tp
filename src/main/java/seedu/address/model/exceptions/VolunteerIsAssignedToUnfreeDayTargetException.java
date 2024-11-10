package seedu.address.model.exceptions;

/**
 * Signals that a volunteer is assigned to an event on the day they are no longer supposed to be free
 */
public class VolunteerIsAssignedToUnfreeDayTargetException extends RuntimeException {

    /**
     * Constructor for this exception
     * @param eventName
     */
    public VolunteerIsAssignedToUnfreeDayTargetException(String eventName) {
        super(String.format("The volunteer is currently assigned to %s that day."
                + " \nPlease unassign them first.", eventName));
    }
}
