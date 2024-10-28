package seedu.address.model.reminder;

import java.time.LocalDateTime;

public class Reminder {
    private ReminderDescription description;
    private LocalDateTime time;

    public Reminder(ReminderDescription description, LocalDateTime time) {
        this.description = description;
        this.time = time;
    }

    public ReminderDescription getDescription() {
        return description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instanceof handles nulls
                && description.equals(((Reminder) other).description)
                && time.equals(((Reminder) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode() + time.hashCode();
    }

    @Override
    public String toString() {
        return description + " at " + time;
    }
}
