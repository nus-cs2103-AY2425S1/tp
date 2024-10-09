package seedu.address.model.role;

/**
 * Represents an Attendee in the event.
 *
 */
public class Attendee extends Role {
    public static final String MESSAGE_CONSTRAINTS = "Attendees should be alphanumeric";

    public static final String ROLE_WORD = "attendee";
    public Attendee() {
        super("attendee");
    }
}
