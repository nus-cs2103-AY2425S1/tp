package seedu.address.model.event;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;


/**
 * Represents an event that a {@code Person} is or was involved in.
 * Guarantees: details (of the event) are present and not null, field values are validated, immutable.
 */
public class Event {
    private final String eventName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Set<Person> attendees = new HashSet<>();
    private final Address location;

    /**
     * Constructs an {@code Event}.
     *
     * @param eventName A valid event name.
     * @param startDate A valid start date.
     * @param endDate A valid end date.
     * @param attendees A set of {@code Person} attending the event.
     */
    public Event(String eventName, LocalDate startDate, LocalDate endDate, Address location, Set<Person> attendees) {
        requireAllNonNull(eventName, startDate, endDate, location, attendees);
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.attendees.addAll(attendees);

    }

    public String getEventName() {
        return eventName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Set<Person> getAttendees() {
        return Collections.unmodifiableSet(attendees);
    }

    public boolean isPersonAttending(Person person) {
        return attendees.contains(person);
    }

    public void removeAttendee(Person person) {
        attendees.remove(person);
    }

    /**
     * Replaces an existing attendee in the attendee list with an edited version.
     * @param personToEdit The original {@code Person} to be replaced in the attendee list.
     * @param editedPerson The modified {@code Person} object to be added to the attendee list.
     */
    public void editAttendee(Person personToEdit, Person editedPerson) {
        assert personToEdit != null;
        assert editedPerson != null;

        if (attendees.contains(personToEdit)) {
            removeAttendee(personToEdit);
            attendees.add(editedPerson);
        }
    }

    public Address getLocation() {
        return location;
    }


    /**
     * Returns true if both events have the same name, date and attendees.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }
        if (otherEvent == null) {
            return false;
        }

        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.startDate.equals(startDate)
                && otherEvent.endDate.equals(endDate)
                && otherEvent.attendees.equals(attendees)
                && location.equals(otherEvent.location);
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
                && startDate.equals(otherEvent.startDate)
                && attendees.equals(otherEvent.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, startDate, endDate, attendees);
    }

    @Override
    public String toString() {
        StringBuilder attendeesString = new StringBuilder();

        for (Person attendee : attendees) {
            attendeesString.append('\n').append(attendee.toString());
        }

        return "Event{"
                + "name='" + eventName + '\''
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", location=" + location
                + ", \nattendees=" + attendeesString
                + '}';
    }
}
