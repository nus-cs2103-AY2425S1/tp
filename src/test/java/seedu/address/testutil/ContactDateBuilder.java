package seedu.address.testutil;

import seedu.address.model.contactdate.ContactDate;

/**
 * A utility class to help with building ContactDate objects.
 */
public class ContactDateBuilder {
    public static final String DEFAULT_DATE = null;
    public static final String DEFAULT_NOTES = "This is a default note";

    private String date;
    private String notes;

    /**
     * Creates a {@code ContactDateBuilder} with the default details.
     */
    public ContactDateBuilder() {
        date = DEFAULT_DATE;
        notes = DEFAULT_NOTES;
    }

    /**
     * Initializes the ContactDateBuilder with the data of {@code contactDateToCopy}.
     */
    public ContactDateBuilder(ContactDate contactDateToCopy) {
        date = contactDateToCopy.value.toString();
        notes = contactDateToCopy.getNotes();
    }

    /**
     * Sets the {@code Date} of the {@code ContactDate} that we are building.
     */
    public ContactDateBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code ContactDate} that we are building.
     */
    public ContactDateBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Builds the ContactDate object.
     */
    public ContactDate build() {
        if (date == null) {
            return ContactDate.createCurrentDate(notes);
        }
        return new ContactDate(date, notes);
    }
}
