package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;


/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_DESCRIPTION = "Likes to eat a lot";
    public static final LocalDateTime DEFAULT_DATETIME = LocalDateTime.of(2020, 10, 10, 10, 10);

    private String personName;
    private ReminderDescription description;
    private LocalDateTime dateTime;

    /**
     * Creates a {@code ReminderBuilder} with default details.
     */
    public ReminderBuilder() {
        personName = DEFAULT_NAME;
        description = new ReminderDescription(DEFAULT_DESCRIPTION);
        dateTime = DEFAULT_DATETIME;
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code toCopy}.
     *
     * @param toCopy The reminder to copy details from.
     */
    public ReminderBuilder(Reminder toCopy) {
        personName = toCopy.getPersonName();
        description = toCopy.getDescription();
        dateTime = toCopy.getDateTime();
    }

    /**
     * Sets the {@code PersonName} of the {@code Reminder} that we are building.
     *
     * @param name The name associated with the reminder.
     * @return This ReminderBuilder with updated name.
     */
    public ReminderBuilder withPersonName(String name) {
        this.personName = name;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Reminder} that we are building.
     *
     * @param description The description of the reminder.
     * @return This ReminderBuilder with updated description.
     */
    public ReminderBuilder withDescription(String description) {
        this.description = new ReminderDescription(description);
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Reminder} that we are building.
     *
     * @param date The date component of the reminder.
     * @param time The time component of the reminder.
     * @return This ReminderBuilder with updated date and time.
     */
    public ReminderBuilder withDateTime(LocalDate date, LocalTime time) {
        this.dateTime = LocalDateTime.of(date, time);
        return this;
    }

    /**
     * Builds and returns a {@code Reminder} with the current attributes of this {@code ReminderBuilder}.
     *
     * @return A Reminder instance with specified name, description, and date-time.
     */
    public Reminder build() {
        return new Reminder(personName, dateTime, description);
    }
}
