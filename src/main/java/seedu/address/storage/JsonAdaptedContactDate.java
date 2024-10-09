package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contactdate.ContactDate;

/**
 * Jackson-friendly version of {@link ContactDate}.
 */
public class JsonAdaptedContactDate {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ContactDate's %s field is missing!";

    private final String date;
    private final String notes;

    /**
     * Constructs a {@code JsonAdaptedContactDate} with the given {@code contactDate}.
     */
    @JsonCreator
    public JsonAdaptedContactDate(@JsonProperty("date") String date, @JsonProperty("notes") String notes) {
        this.date = date;
        this.notes = notes;
    }

    /**
     * Converts a given {@code ContactDate} into this class for Jackson use.
     */
    public JsonAdaptedContactDate(ContactDate source) {
        date = source.value.toString();
        notes = source.getNotes();
    }
    /**
     * Converts this Jackson-friendly adapted contact date object into the model's {@code ContactDate} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact date.
     */
    public ContactDate toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        if (!ContactDate.isValidContactDate(date)) {
            throw new IllegalValueException(ContactDate.MESSAGE_CONSTRAINTS);
        }
        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "notes"));
        }
        return new ContactDate(date, notes);
    }
}
