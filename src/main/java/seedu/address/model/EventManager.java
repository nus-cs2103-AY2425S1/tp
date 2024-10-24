package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.volunteer.Volunteer;

/**
 * Manages the event-related logic.
 */
public class EventManager {
    private final UniqueEventList events = new UniqueEventList();

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Assigns a volunteer to an event.
     * @param volunteer Volunteer to be assigned.
     * @param event Event to be assigned to.
     * @throws DuplicateAssignException if the volunteer is already assigned to the event.
     */
    public void assignVolunteerToEvent(Volunteer volunteer, Event event) throws DuplicateAssignException,
            OverlappingAssignException {

        if (event.getVolunteers().contains(volunteer.getName().fullName)) {
            throw new DuplicateAssignException();
        }
        for (Event e : getEventsFromListOfNames(volunteer.getEvents())) {
            if (e.isOverlappingWith(event)) {
                throw new OverlappingAssignException();
            }
        }
        event.assignVolunteer(volunteer.getName().fullName);
    }

    /**
     * Retreives an event object from the events list using its name.
     * @param eventName Event name to search for
     * @return Event object retrieved from list
     * @throws EventNotFoundException if event with the provided name does not exist within the events list.
     */

    public Event getEventFromName(String eventName) throws EventNotFoundException {
        for (Event e : events) {
            if (e.getName().toString().equals(eventName)) {
                return e;
            }
        }
        throw new EventNotFoundException();
    }


    /**
     * Returns a list of event objects using a provided list of event names.
     * @param eventNames String list of event names.
     * @return Event list of events.
     */
    public List<Event> getEventsFromListOfNames(List<String> eventNames) {
        ArrayList<Event> events = new ArrayList<>();
        for (String s : eventNames) {
            events.add(getEventFromName(s));
        }
        return events;
    }


    /**
     * Unassigns a volunteer from an event.
     * @param volunteer Volunteer to be unassigned.
     * @param event Event to be unassigned from.
     * @throws NotAssignedException if the volunteer is not assigned to the event.
     */
    public void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws NotAssignedException {
        if (!event.getVolunteers().contains(volunteer.getName().fullName)) {
            throw new NotAssignedException();
        }
        event.unassignVolunteer(volunteer.getName().fullName);
    }

    public void unassignVolunteerFromAllEvents(Volunteer volunteer) {
        events.forEach(event -> event.unassignVolunteer(volunteer.getName().fullName));
    }

    public UniqueEventList getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventManager)) {
            return false;
        }

        EventManager otherManager = (EventManager) other;
        return events.equals(otherManager.events);
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
