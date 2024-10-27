package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.role.athlete.SportString;


/**
 * Represents an {@code Event} in the address book.
 */
public class Event {
    // Identity fields
    private final EventName name;
    private final SportString sport;
    private final Venue venue;
    private final Set<Person> participants = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(EventName name, SportString sport, Venue venue, Set<Person> participants) {
        requireAllNonNull(name);
        this.name = name;
        this.sport = sport;
        this.venue = venue;
        this.participants.addAll(participants);
    }

    /**
     * Returns the name of the event.
     */
    public EventName getName() {
        return name;
    }

    /**
     * Returns the sport of the event.
     */
    public SportString getSport() {
        return sport;
    }

    /**
     * Returns the venue of the event.
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * Returns an immutable participant set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getParticipants() {
        return Set.copyOf(participants);
    }

    /**
     * Returns true if both events have the same name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event otherEvent)) {
            return false;
        }

        return name.equals(otherEvent.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}
