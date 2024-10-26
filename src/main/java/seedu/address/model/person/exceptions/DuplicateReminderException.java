package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Reminder (Reminder are considered duplicates if they have the
 * same date, description and person to meet).
 */
public class DuplicateReminderException extends RuntimeException {
    public DuplicateReminderException() {
        super("Operation would result in duplicate reminders");
    }
}
