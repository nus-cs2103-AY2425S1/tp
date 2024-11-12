package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Reminder;

/**
 * A utility class to help with building Reminder Objects
 */
public class ReminderBuilder {
    public static final String DEFAULT_NAME = "Joe Mama";
    public static final String DEFAULT_DATE = "12-12-2024";
    public static final String DEFAULT_DESCRIPTION = "Interview for SWE position at ABC startup";
    private Name name;
    private String date;
    private String description;

    /**
     * Creates a {@code ReminderBuilder} with the default details.
     */
    public ReminderBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.date = DEFAULT_DATE;
        this.description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        this.name = reminderToCopy.getPersonToMeet();
        this.date = reminderToCopy.getReminderDateAsString();
        this.description = reminderToCopy.getReminderDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Reminder build() {
        return new Reminder(this.date, this.description, this.name);
    }

}
