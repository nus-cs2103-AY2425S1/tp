package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.ReadOnlyEventManager;

/**
 * An Immutable EventManager that is serializable to JSON format.
 */
@JsonRootName(value = "eventmanager")
public class JsonSerializableEventManager {

    public static final String MESSAGE_DUPLICATE_EVENT = "Event's list contains duplicate person(s).";
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEventManager(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyEventManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEventManager}.
     */
    public JsonSerializableEventManager(ReadOnlyEventManager source) {
        events.addAll(source.getEventList()
                        .stream()
                        .map(JsonAdaptedEvent::new)
                        .toList());
    }

    /**
     * Converts this event manager into the model's {@code EventManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventManager toModelType() throws IllegalValueException {
        EventManager eventManager = new EventManager();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (eventManager.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventManager.addEvent(event);
        }
        return eventManager;
    }
}
