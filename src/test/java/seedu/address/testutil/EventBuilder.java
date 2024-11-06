package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.person.role.athlete.SportString;

/**
 * A utility class to help with building {@link Event} objects.
 */
public class EventBuilder {
    public static final String DEFAULT_NAME = "IFG";
    public static final String DEFAULT_VENUE = "UTown";

    public static final String DEFAULT_SPORT = "Chess";

    private EventName name;
    private Venue venue;

    private SportString sport;

    private Set<Person> participants;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        venue = new Venue(DEFAULT_VENUE);
        sport = new SportString(DEFAULT_SPORT);
        participants = new HashSet<>();
    }

    /**
     * Initializes the {@code EventBuilder} with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        venue = eventToCopy.getVenue();
        sport = eventToCopy.getSport();
        participants = new HashSet<>(eventToCopy.getParticipants());
    }

    /**
     * Sets the {@link EventName} of the {@link Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
        return this;
    }

    /**
     * Sets the {@link Venue} of the {@link Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@link SportString} of the {@link Event} that we are building.
     */
    public EventBuilder withSport(String sport) {
        this.sport = new SportString(sport);
        return this;
    }

    /**
     * Parses the {@code participants} into a {@code Set<Person>} and set it to the {@link Event} that we are building.
     */
    public EventBuilder withParticipants(Person ... participants) {
        this.participants = Set.of(participants);
        return this;
    }


    public Event build() {
        return new Event(name, sport, venue, participants);
    }
}
