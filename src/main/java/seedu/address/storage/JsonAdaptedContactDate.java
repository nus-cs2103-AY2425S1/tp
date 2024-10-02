package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contactdate.ContactDate;

/**
 * Jackson-friendly version of {@link ContactDate}.
 */
public class JsonAdaptedContactDate {
    private final String contactDate;

    /**
     * Constructs a {@code JsonAdaptedContactDate} with the given {@code contactDate}.
     */
    @JsonCreator
    public JsonAdaptedContactDate(String contactDate) {
        this.contactDate = contactDate;
    }

    /**
     * Converts a given {@code ContactDate} into this class for Jackson use.
     */
    public JsonAdaptedContactDate(ContactDate source) {
        contactDate = source.toString();
    }

    @JsonValue
    public String getContactDate() {
        return contactDate;
    }

    /**
     * Converts this Jackson-friendly adapted contact date object into the model's {@code ContactDate} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact date.
     */
    public ContactDate toModelType() throws IllegalValueException {
        if (!ContactDate.isValidContactDate(contactDate)) {
            throw new IllegalValueException(ContactDate.MESSAGE_CONSTRAINTS);
        }
        return new ContactDate(contactDate);
    }
}
