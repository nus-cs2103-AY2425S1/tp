package seedu.address.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final JsonAdaptedTime time;
    private final String venue;
    private final String celebrityName;
    private final Set<String> contactNames = new HashSet<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
                            @JsonProperty("time") JsonAdaptedTime time,
                            @JsonProperty("venue") String venue,
                            @JsonProperty("celebrityName") String celebrityName,
                            @JsonProperty("contactNames") List<String> contactNames) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.celebrityName = celebrityName;
        this.contactNames.addAll(contactNames);
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().getEventName();
        time = new JsonAdaptedTime(source.getTime());
        venue = source.getVenue().getVenue();
        celebrityName = source.getCelebrity().getName().fullName;
        contactNames.addAll(source.getContacts().stream()
                .map(contact -> contact.getName().fullName)
                .toList());
    }

    public String getCelebrityName() {
        return celebrityName;
    }

    public Set<String> getContactNames() {
        return contactNames;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType(Person celebrity, Set<Person> eventContacts) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName())
            );
        }
        final EventName eventName = new EventName(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        final Time eventTime = time.toModelType();

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        final Venue eventVenue = new Venue(venue);

        if (celebrity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }

        return new Event(eventName, eventTime, eventVenue, celebrity, eventContacts);
    }

}
