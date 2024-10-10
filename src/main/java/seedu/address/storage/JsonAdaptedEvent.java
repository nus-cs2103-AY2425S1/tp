package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;

public class JsonAdaptedEvent {

    private final String eventName;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given {@code eventName}.
     */
    @JsonCreator
    public JsonAdaptedEvent(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.eventName = source.value;
    }

    @JsonValue
    public String getEventName() {
        return eventName;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if(!Event.isValidEvent(eventName)) {
            throw new IllegalValueException(Event.MESSAGE_CONSTRAINTS);
        }
        return new Event(eventName);
    }

}
