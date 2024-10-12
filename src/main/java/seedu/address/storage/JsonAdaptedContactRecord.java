package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contactrecord.ContactRecord;

/**
 * Jackson-friendly version of {@link ContactRecord}.
 */
public class JsonAdaptedContactRecord {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ContactRecord's %s field is missing!";

    private final String date;
    private final String notes;

    /**
     * Constructs a {@code JsonAdaptedContactRecord} with the given {@code contactRecord}.
     */
    @JsonCreator
    public JsonAdaptedContactRecord(@JsonProperty("date") String date, @JsonProperty("notes") String notes) {
        this.date = date;
        this.notes = notes;
    }

    /**
     * Converts a given {@code ContactRecord} into this class for Jackson use.
     */
    public JsonAdaptedContactRecord(ContactRecord source) {
        date = source.value.toString();
        notes = source.getNotes();
    }
    /**
     * Converts this Jackson-friendly adapted contact date object into the model's {@code ContactRecord} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact date.
     */
    public ContactRecord toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        if (!ContactRecord.isValidContactRecord(date)) {
            throw new IllegalValueException(ContactRecord.MESSAGE_CONSTRAINTS);
        }
        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "notes"));
        }
        return new ContactRecord(date, notes);
    }
}
