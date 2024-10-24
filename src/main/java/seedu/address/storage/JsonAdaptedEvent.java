package seedu.address.storage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;


/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    private final String eventName;
    private final LocalDate eventDate;
    private final Set<JsonAdaptedPerson> attendees = new HashSet<>();
    private final Address location;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName,
                            @JsonProperty("eventDate") LocalDate eventDate,
                            @JsonProperty("attendees") Set<JsonAdaptedPerson> attendees,
                            @JsonProperty("location") Address location) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        if (attendees != null) {
            this.attendees.addAll(attendees);
        }
        this.location = location;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventName = source.getEventName();
        eventDate = source.getDate();
        attendees.addAll(source.getAttendees().stream().map(JsonAdaptedPerson::new).collect(Collectors.toSet()));
        location = source.getLocation();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final Set<Person> modelAttendees = new HashSet<>();
        for (JsonAdaptedPerson attendee : attendees) {
            modelAttendees.add(attendee.toModelType());
        }
        return new Event(eventName, eventDate, location, modelAttendees);

    }
}
