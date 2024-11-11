package seedu.address.model.reminder;

/**
 * Signals that the specified reminder could not be found.
 */
public class ReminderNotFoundException extends RuntimeException {
    /**
     * Constructs a {@code ReminderNotFoundException} with a default error message.
     */
    public ReminderNotFoundException() {
        super("Reminder not found!");
    }
}
