package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's reminder for scheduled appointment in the address book.
 */
public class Reminder {
    public final String reminderTime;

    /**
     * Constructs a {@code Reminder} object with the given appointment date and time.
     *
     * @param reminderTime The time before the appointment when the reminder should trigger.
     * @throws NullPointerException if the {@code appointmentDateTime} or {@code reminderTime} is null.
     */
    public Reminder(String reminderTime) {
        requireNonNull(reminderTime);
        this.reminderTime = reminderTime;
    }

    public String getReminderTime() {
        return this.reminderTime;
    }

    @Override
    public String toString() {
        return this.reminderTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instance of handles nulls
                && reminderTime.equals(((Reminder) other).reminderTime));
    }

    @Override
    public int hashCode() {
        return reminderTime.hashCode();
    }
}
