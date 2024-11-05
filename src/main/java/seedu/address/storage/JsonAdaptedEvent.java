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
    public static final String MESSAGE_CELEBRITY_IN_CONTACT = "Celebrity cannot be a contact in contact list";

    private final String name;
    private final JsonAdaptedTime time;
    private final String venue; // Optional, can be null
    private final String celebrityName;
    private final Set<String> contactNames = new HashSet<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("time") JsonAdaptedTime time,
            @JsonProperty("venue") String venue, @JsonProperty("celebrityName") String celebrityName,
            @JsonProperty("contactNames") List<String> contactNames) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.celebrityName = celebrityName;
        this.contactNames.addAll(contactNames);
    }

    /**
     * Constructs a {@code JsonAdaptedEvent} without a venue, with the given event details.
     */
    public JsonAdaptedEvent(String name, JsonAdaptedTime time, String celebrityName, List<String> contactNames) {
        this(name, time, null, celebrityName, contactNames);
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().getEventName();
        time = new JsonAdaptedTime(source.getTime());
        venue = source.getVenue().map(Venue::toString).orElse(null);
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
     * Constructor for {@code JsonAdaptedEvent} in general
     * Returns a {@code JsonAdaptedEvent} object using the different constructors given the respective fields.
     */
    public static JsonAdaptedEvent createJsonAdaptedEvent(String name, JsonAdaptedTime time, String venue,
            String celebrityName, List<String> contactNames) {
        if (venue == null) {
            return new JsonAdaptedEvent(name, time, celebrityName, contactNames);
        } else {
            return new JsonAdaptedEvent(name, time, venue, celebrityName, contactNames);
        }
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

        Venue eventVenue = null;
        if (venue != null) {
            eventVenue = new Venue(venue);
        }

        if (celebrity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }

        if (eventContacts.contains(celebrity)) {
            throw new IllegalValueException(MESSAGE_CELEBRITY_IN_CONTACT);
        }

        return Event.createEvent(eventName, eventTime, eventVenue, celebrity, eventContacts);
    }

}
