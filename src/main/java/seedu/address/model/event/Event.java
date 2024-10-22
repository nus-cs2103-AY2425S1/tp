package seedu.address.model.event;

import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event {
    private final EventName name;
    private final Time time;
    private final Venue venue;
    private final Person celebrity;
    private final List<Person> contacts;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name, Time time, Venue venue, Person person, List<Person> contacts) {
        this.name = name;
        this.time = time;
        this.venue = venue;
        this.celebrity = person;
        this.contacts = contacts;
    }

    public EventName getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public Venue getVenue() {
        return venue;
    }

    public Person getCelebrity() {
        return celebrity;
    }

    public List<Person> getContacts() {
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
