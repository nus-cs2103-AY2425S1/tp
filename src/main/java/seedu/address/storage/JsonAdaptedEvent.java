package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.person.role.athlete.SportString;


/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String sport;
    private final String venue;
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
                            @JsonProperty("sport") String sport,
                            @JsonProperty("venue") String venue,
                            @JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.name = name;
        this.sport = sport;
        this.venue = venue;
        if (persons != null) {
            this.persons.addAll(persons);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().toString();
        sport = source.getSport().toString();
        venue = source.getVenue().toString();
        persons.addAll(source.getParticipants().stream()
                .map(JsonAdaptedPerson::new)
                .toList());
    }


    @JsonProperty("name")
    public String getEventName() {
        return name;
    }

    @JsonProperty("sport")
    public String getSport() {
        return sport;
    }

    @JsonProperty("venue")
    public String getVenue() {
        return venue;
    }

    @JsonProperty("persons")
    public List<JsonAdaptedPerson> getPersons() {
        return persons;
    }

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

        final SportString eventSport = new SportString(sport);

        final Venue eventVenue = new Venue(venue);

        final List<Person> eventPersons = new ArrayList<>();
        for (JsonAdaptedPerson person : persons) {
            eventPersons.add(person.toModelType());
        }

        return new Event(eventName, eventSport, eventVenue, new HashSet<>(eventPersons));
    }
}
