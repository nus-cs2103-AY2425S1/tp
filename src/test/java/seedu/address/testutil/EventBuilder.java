package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Tech Conference 2024";

    private String name;
    private Set<Person> attendees;
    private Set<Person> vendors;
    private Set<Person> sponsors;
    private Set<Person> volunteers;

    /**
     * Creates an {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = DEFAULT_NAME;
        attendees = new HashSet<>();
        vendors = new HashSet<>();
        sponsors = new HashSet<>();
        volunteers = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        attendees = new HashSet<>(eventToCopy.getAttendees());
        vendors = new HashSet<>(eventToCopy.getVendors());
        sponsors = new HashSet<>(eventToCopy.getSponsors());
        volunteers = new HashSet<>(eventToCopy.getVolunteers());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the attendees of the {@code Event} that we are building.
     */
    public EventBuilder withAttendees(Set<Person> attendees) {
        this.attendees = attendees;
        return this;
    }

    /**
     * Sets the vendors of the {@code Event} that we are building.
     */
    public EventBuilder withVendors(Set<Person> vendors) {
        this.vendors = vendors;
        return this;
    }

    /**
     * Sets the sponsors of the {@code Event} that we are building.
     */
    public EventBuilder withSponsors(Set<Person> sponsors) {
        this.sponsors = sponsors;
        return this;
    }

    /**
     * Sets the volunteers of the {@code Event} that we are building.
     */
    public EventBuilder withVolunteers(Set<Person> volunteers) {
        this.volunteers = volunteers;
        return this;
    }

    /**
     * Builds and returns the {@code Event} object.
     *
     * @return The constructed {@code Event} object.
     */
    public Event build() {
        return new Event(name, attendees, vendors, sponsors, volunteers);
    }
}
