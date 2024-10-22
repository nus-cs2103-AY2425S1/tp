package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String time;
    private final String venue;
    private final JsonAdaptedPerson celebrity;
    private final List<JsonAdaptedPerson> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("time") String time,
                            @JsonProperty("venue") String venue,
                            @JsonProperty("celebrity") JsonAdaptedPerson celebrity,
                            @JsonProperty("contacts") List<JsonAdaptedPerson> contacts) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.celebrity = celebrity;
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().getEventName();
        time = source.getTime().getTime();
        venue = source.getVenue().getVenue();
        celebrity = new JsonAdaptedPerson(source.getCelebrity());
        contacts.addAll(source.getContacts().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Person> eventContacts = new ArrayList<>();
        for (JsonAdaptedPerson contact : contacts) {
            eventContacts.add(contact.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName())
            );
        }
        final EventName eventName = new EventName(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        final Time eventTime = new Time(time);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        final Venue eventVenue = new Venue(venue);

        if (celebrity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        final Person eventCelebrity = celebrity.toModelType();

        return new Event(eventName, eventTime, eventVenue, eventCelebrity, eventContacts);
    }

}
