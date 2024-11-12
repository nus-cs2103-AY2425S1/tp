package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Time;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Interview";
    public static final LocalDateTime DEFAULT_START_TIME = LocalDateTime.parse("2015-02-20T06:30:00");
    public static final LocalDateTime DEFAULT_END_TIME = LocalDateTime.parse("2015-02-20T07:30:00");
    public static final String DEFAULT_VENUE = "Esplanade";

    private EventName name;
    private Time time;
    private Venue venue;
    private Person celebrity;
    private Set<Person> contacts;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        time = new Time(DEFAULT_START_TIME, DEFAULT_END_TIME);
        venue = new Venue(DEFAULT_VENUE);
        celebrity = new PersonBuilder().build();
        contacts = new HashSet<>(TypicalPersons.getTypicalPersons());
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        time = eventToCopy.getTime();
        venue = eventToCopy.getVenue().orElse(null);
        celebrity = eventToCopy.getCelebrity();
        contacts = eventToCopy.getContacts();
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
        return this;
    }

    /**
     * Sets the {@code Celebrity} of the {@code Event} that we are building.
     */
    public EventBuilder withCelebrity(Person celebrity) {
        this.celebrity = Person.createPerson(celebrity.getName(), celebrity.getPhone(),
                celebrity.getEmail().orElse(null), celebrity.getAddress().orElse(null),
                celebrity.getTags());
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Event} that we are building.
     */
    public EventBuilder withTime(LocalDateTime startTime, LocalDateTime endTime) {
        this.time = new Time(startTime, endTime);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code Contacts} of the {@code Event} that we are building.
     */
    public EventBuilder withContacts(Set<Person> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Event build() {
        return Event.createEvent(name, time, venue, celebrity, contacts);
    }

}
