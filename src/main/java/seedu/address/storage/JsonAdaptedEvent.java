package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    private final String eventName;

    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName) {
        this.eventName = eventName.trim();
    }

    public JsonAdaptedEvent(Event source) {
        eventName = source.toString();
    }

    @JsonProperty("eventName")
    public String getEventName() {
        return eventName;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (!Event.isValidEvent(eventName)) {
            throw new IllegalValueException(Event.MESSAGE_CONSTRAINTS);
        }
        return new Event(eventName);
    }

}
