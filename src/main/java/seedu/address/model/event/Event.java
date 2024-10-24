package seedu.address.model.event;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;


/**
 * Represents an event that a {@code Person} is or was involved in.
 * Guarantees: details (of the event) are present and not null, field values are validated, immutable.
 */
public class Event {
    private final String eventName;
    private final LocalDate date;
    private final Set<Person> attendees = new HashSet<>();

    /**
     * Constructs an {@code Event}.
     *
     * @param eventName A valid event name.
     * @param date A valid date.
     * @param attendees A set of {@code Person} attending the event.
     */
    public Event(String eventName, LocalDate date, Set<Person> attendees) {
        requireAllNonNull(eventName, date, attendees);
        this.eventName = eventName;
        this.date = date;
        this.attendees.addAll(attendees);
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<Person> getAttendees() {
        return Collections.unmodifiableSet(attendees);
    }

    public boolean isPersonAttending(Person person) {
        return attendees.contains(person);
    }

    // TODO: Implement Location for Event, then update equality to check for
    //       name, date, and location


    /**
     * Returns true if both events have the same name, date and attendees.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getAttendees().equals(getAttendees());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventName.equals(otherEvent.eventName)
                && date.equals(otherEvent.date)
                && attendees.equals(otherEvent.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, date, attendees);
    }

    @Override
    public String toString() {
        StringBuilder attendeesString = new StringBuilder();

        for (Person attendee : attendees) {
            attendeesString.append('\n').append(attendee.toString());
        }

        return "Event{"
                + "name='" + eventName + '\''
                + ", date=" + date
                + ", \nattendees=" + attendeesString
                + '}';
    }
}
