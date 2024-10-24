package seedu.ddd.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.event.common.EventId;

class JsonAdaptedContactId {
    private final int contactId;

    /**
     * Constructs a {@code JsonAdaptedEventId} with the given {@code eventId}.
     */
    @JsonCreator
    public JsonAdaptedContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Converts a given {@code EventId} into this class for Jackson use.
     */
    public JsonAdaptedContactId(ContactId source) {
        contactId = source.contactId;
    }

    @JsonValue
    public int getEventId() {
        return contactId;
    }

    /**
     * Converts this Jackson-friendly adapted eventId object into the model's {@code EventId} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ContactId toModelType() throws IllegalValueException {
        if (!ContactId.isValidContactId(contactId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        return new ContactId(contactId);
    }
}
