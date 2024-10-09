package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
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
    private final JsonAdaptedPerson mainContact;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("time") String time,
                             @JsonProperty("venue") String venue,
                            @JsonProperty("contact") JsonAdaptedPerson mainContact) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.mainContact = mainContact;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().getName();
        time = source.getTime().getTime();
        venue = source.getVenue().getVenue();
        mainContact = new JsonAdaptedPerson(source.getPerson());
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final Name eventName = new Name(name);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        final Time eventTime = new Time(time);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        final Venue eventVenue = new Venue(venue);

        if (mainContact == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        final Person eventMainContact = mainContact.toModelType();

        return new Event(eventName, eventTime, eventVenue, eventMainContact);
    }

}
