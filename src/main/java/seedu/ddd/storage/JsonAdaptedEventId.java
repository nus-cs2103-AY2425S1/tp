package seedu.ddd.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.event.common.EventId;

class JsonAdaptedEventId {
    private final int eventId;

    /**
     * Constructs a {@code JsonAdaptedEventId} with the given {@code eventId}.
     */
    @JsonCreator
    public JsonAdaptedEventId(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Converts a given {@code EventId} into this class for Jackson use.
     */
    public JsonAdaptedEventId(EventId source) {
        eventId = source.eventId;
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
    public EventId toModelType() throws IllegalValueException {
        if (!EventId.isValidEventId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        return new EventId(eventId);
    }
}
