package seedu.address.model.reminder;
import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Reminder associated with a person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {
    private final String personName;
    private final LocalDateTime dateTime;
    private final ReminderDescription description;

    /**
     * Creates a Reminder with the specified person, date and time, and description.
     *
     * @param person       the person associated with the reminder
     * @param dateTime     the date and time of the reminder
     * @param description  the description of the reminder
     */
    public Reminder(String person, LocalDateTime dateTime, ReminderDescription description) {
        this.personName = Objects.requireNonNull(person, "Person name cannot be null");
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
    }

    public String getPersonName() {
        return personName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ReminderDescription getDescription() {
        return description;
    }

    /**
     * Returns true if both reminders are associated with the same person, date and time,
     * and have the same description. This defines a stronger notion of equality between two reminders.
     *
     * @param other the other object to compare to
     * @return true if both reminders are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return personName.equals(otherReminder.personName)
                && dateTime.equals(otherReminder.dateTime)
                && description.equals(otherReminder.description);
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
                && otherReminder.getDateTime().equals(getDateTime());
    }
    @Override
    public int hashCode() {
        return Objects.hash(personName, dateTime, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", personName)
                .add("Date and time", this.getFormattedDateTime())
                .add("description", description)
                .toString();
    }

    public Reminder getReminderWithFullName(String fullName) {
        return new Reminder(fullName, this.dateTime, this.description);
    }

    /**
     * Returns the formatted date and time string.
     * @return formatted date and time string
     */
    public String getFormattedDateTime() {
        String dateString = this.dateTime.toLocalDate().toString();
        String timeString = this.dateTime.toLocalTime().toString();
        return dateString + " " + timeString;
    }

}
