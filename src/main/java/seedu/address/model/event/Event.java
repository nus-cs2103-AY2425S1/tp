package seedu.address.model.event;

import seedu.address.model.person.Person;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event {
    private Name name;
    private Time time;
    private Venue venue;
    private Person person;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Time time, Venue venue, Person person) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.person = person;
    }

    public Name getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public Venue getVenue() {
        return venue;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return name + " " + time + " " + venue + " " + person;
    }

    @Override
    public boolean equals(Object other) {
        return false; //To implement
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        return this.equals(otherEvent); //To implement
    }
}
