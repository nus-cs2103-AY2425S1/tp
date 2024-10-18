package seedu.address.testutil;

import seedu.address.model.contactrecord.ContactRecord;

/**
 * A utility class to help with building ContactRecord objects.
 */
public class ContactRecordBuilder {
    public static final String DEFAULT_DATE = null;
    public static final String DEFAULT_NOTES = "This is a default note";

    private String date;
    private String notes;

    /**
     * Creates a {@code ContactRecordBuilder} with the default details.
     */
    public ContactRecordBuilder() {
        date = DEFAULT_DATE;
        notes = DEFAULT_NOTES;
    }

    /**
     * Initializes the ContactRecordBuilder with the data of {@code contactRecordToCopy}.
     */
    public ContactRecordBuilder(ContactRecord contactRecordToCopy) {
        date = contactRecordToCopy.value.toString();
        notes = contactRecordToCopy.getNotes();
    }

    /**
     * Sets the {@code Date} of the {@code ContactRecord} that we are building.
     */
    public ContactRecordBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code ContactRecord} that we are building.
     */
    public ContactRecordBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Builds the ContactRecord object.
     */
    public ContactRecord build() {
        if (date == null) {
            return ContactRecord.createCurrentRecord(notes);
        }
        return new ContactRecord(date, notes);
    }
}
