package seedu.address.model.reminder;
import java.time.LocalDateTime;

/**
 * Represents a reminder with a description and a time.
 */
public class Reminder {
    private ReminderDescription description;
    private LocalDateTime time;

    /**
     * Constructs a {@code Reminder} with the specified description and time.
     *
     * @param description The description of the reminder.
     * @param time The time of the reminder.
     */
    public Reminder(ReminderDescription description, LocalDateTime time) {
        this.description = description;
        this.time = time;
    }

    /**
     * Returns the description of the reminder.
     *
     * @return The reminder's description.
     */
    public ReminderDescription getDescription() {
        return description;
    }

    /**
     * Returns the time of the reminder.
     *
     * @return The reminder's time.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Checks if this reminder is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instanceof handles nulls
                && description.equals(((Reminder) other).description)
                && time.equals(((Reminder) other).time)); // state check
    }

    /**
     * Returns the hash code of this reminder.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return description.hashCode() + time.hashCode();
    }

    /**
     * Checks if this reminder is the same as another reminder.
     * Two reminders are considered the same if they have identical descriptions and times.
     *
     * @param otherReminder The other reminder to compare with.
     * @return True if the reminders are the same, false otherwise.
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getDescription().equals(getDescription())
                && otherReminder.getTime().equals(getTime());
    }

    /**
     * Returns a string representation of the reminder, including its description and time.
     *
     * @return A string representation of the reminder.
     */
    @Override
    public String toString() {
        return description + " at " + time;
    }
}
