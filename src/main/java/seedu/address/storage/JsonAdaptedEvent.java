package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;

    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name) {
        this.name = name;
    }

    //    public JsonAdaptedEvent(Event source) {
    //        name = source.getName().eventName;
    //    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }

        if (!EventName.isValidEventName(name)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }

        final EventName eventName = new EventName(name);
        return new Event(eventName);
    }
}
