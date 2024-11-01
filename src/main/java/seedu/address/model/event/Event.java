package seedu.address.model.event;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Represents an Event in the address book.
 * Guarantees: Name, Time and Celebrity are present and not null, all field values are validated.
 */
public class Event {
    private final EventName name;
    private final Time time;
    private final Venue venue; // Optional, can be null
    private final Person celebrity;
    private final Set<Person> contacts = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name, Time time, Venue venue, Person person, Set<Person> contacts) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.celebrity = person;
        this.contacts.addAll(contacts);
    }

    /**
     * Constructor for Event without Venue
     * Every field must be present and not null.
     */
    public Event(EventName name, Time time, Person person, Set<Person> contacts) {
        this.name = name;
        this.time = time;
        this.venue = null;
        this.celebrity = person;
        this.contacts.addAll(contacts);
    }

    /**
     * Constructor for Person in general
     * Returns a Person object using the different constructors given the respective fields.
     */
    public static Event createEvent(EventName name, Time time, Venue venue, Person person, Set<Person> contacts) {
        if (venue == null) {
            return new Event(name, time, person, contacts);
        } else {
            return new Event(name, time, venue, person, contacts);
        }
    }

    public EventName getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public Optional<Venue> getVenue() {
        return Optional.ofNullable(venue);
    }

    public Person getCelebrity() {
        return celebrity;
    }

    public Set<Person> getContacts() {
        return contacts;
    }

    public String getContactsString() {
        return contacts.stream().collect(StringBuilder::new, (sb, p) -> sb.append(p.getName().fullName)
                        .append(" ").append(p.getPhone().value).append("\n"),
                StringBuilder::append).toString();
    }

    @Override
    public String toString() {
        return name + " " + time + " " + venue + " " + celebrity + " " + contacts;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        if ((venue == null) || (otherEvent.venue == null)) {
            return false;
        }
        return name.equals(otherEvent.name)
                && time.equals(otherEvent.time)
                && venue.equals(otherEvent.venue)
                && celebrity.equals(otherEvent.celebrity)
                && contacts.equals(otherEvent.contacts);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, venue, celebrity, contacts);
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        return this.equals(otherEvent);
    }
}
