package seedu.address.model.role;

/**
 * Represents a Volunteer in the event.
 */
public class Volunteer extends Role {
    public static final String MESSAGE_CONSTRAINTS = "Volunteers should be alphanumeric";

    public static final String ROLE_WORD = "volunteer";
    public Volunteer() {
        super("volunteer");
    }
}
