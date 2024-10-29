package seedu.ddd.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.common.Id;

class JsonAdaptedId {
    private final int eventId;

    /**
     * Constructs a {@code JsonAdaptedEventId} with the given {@code eventId}.
     */
    @JsonCreator
    public JsonAdaptedId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Converts a given {@code EventId} into this class for Jackson use.
     */
    public JsonAdaptedId(Id source) {
        eventId = source.id;
    }

    @JsonValue
    public int getEventId() {
        return eventId;
    }

    /**
     * Converts this Jackson-friendly adapted eventId object into the model's {@code EventId} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Id toModelType() throws IllegalValueException {
        if (!Id.isValidId(eventId)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(eventId);
    }
}
