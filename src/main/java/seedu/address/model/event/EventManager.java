package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonInEventPredicate;

/**
 * Wraps all {@code Event} and abstracts away
 * the logic of dealing with a large number of
 * {@code Event} instances.
 */
public class EventManager implements ReadOnlyEventManager {
    // TODO: enforce uniqueness?
    private final EventList eventList;

    /**
     * Initialises an empty EventManager.
     */
    public EventManager() {
        eventList = new EventList();
    }

    /**
     * Creates an EventManager using the Events in the {@code toBeCopied}
     */
    public EventManager(ReadOnlyEventManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // Minimal Functions needed to deal with Events

    /**
     * Adds an event into the event list.
     *
     * @param event A event to be added.
     */
    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    /**
     * Removes an event from the event list.
     *
     * @param event An event to be removed.
     */
    public void removeEvent(Event event) {
        // probably need to add a check here
        this.eventList.remove(event);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the event list.
     *
     * @param target The event to be replaced.
     * @param editedEvent The event that replaces the target.
     */
    public void setEvent(Event target, Event editedEvent) {
        this.eventList.setEvent(target, editedEvent);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        this.eventList.setEvents(events);
    }

    /**
     * Checks if the {@code EventManager} contains the specified {@code Event}.
     *
     * @param event The {@code Event} to check.
     * @return {@code true} if the event is present in the {@code eventList}, {@code false} otherwise.
     * @throws NullPointerException if the {@code event} is null.
     */
    public boolean hasEvent(Event event) {
        return this.eventList.contains(event);
    }

    @Override
    public ObservableList<Event> getEventList() {
        return eventList.asUnmodifiableObservableList();
    }

    /**
     * Returns a {@PersonInEventPredicate} that tests if a {@code person} is inside a specified {@code event}.
     *
     * @param event Event that you want to test whether the person is in.
     * @return {@code PersonInEventPredicate} object.
     */
    public PersonInEventPredicate getPersonInEventPredicate(Event event) {
        assert this.eventList.contains(event) : "This method should only take in events that exist in the eventList.";

        // Search through the unmodifiable list for the event, else throw EventNotFoundException
        // Unmodifiable List is not strong enough to ensure that the event is not modified
        Event equalEvent = this.eventList.asUnmodifiableObservableList().stream().filter(event::equals)
                .findFirst().orElseThrow(EventNotFoundException::new);

        // Use a defensive copy of the event in the predicate.
        return new PersonInEventPredicate(new Event(equalEvent));
    }

    /**
     * Resets the existing data of this {@code EventManager} with {@code newData}.
     */
    public void resetData(ReadOnlyEventManager newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    /**
     * Edits all the people that match personToEdit in all events
     * @param personToEdit person that will be edited
     * @param editedPerson new person that will replace personToEdit
     */
    public void editAllPersonsInEvents(Person personToEdit, Person editedPerson) {
        requireAllNonNull(personToEdit, editedPerson);
        eventList.forEach(event -> event.editPerson(personToEdit, editedPerson));
    }

    /**
     * Edits all the people that match personToEdit in all events
     * @param personToRemove person that will be edited
     */
    public void removeAllPersonsInEvents(Person personToRemove) {
        requireNonNull(personToRemove);
        eventList.forEach(event -> event.removePerson(personToRemove));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventManager)) {
            return false;
        }

        EventManager otherEventManager = (EventManager) other;
        return eventList.equals(otherEventManager.eventList);
    }

    /**
     * Updates the UI for each event in the event list.
     * <p>
     * This method iterates over all events in the {@code eventList} and calls their
     * {@code updateUi()} method, ensuring that any changes to event data are reflected
     * in the user interface.
     * </p>
     */
    public void updateEvents() {
        for (Event event : this.eventList) {
            event.updateUi();
        }
    }

    /**
     * Clears all contact lists from every event in the event list.
     * This includes attendees, volunteers, vendors, and sponsors in each event.
     */
    public void clearAllContactsFromAllEvents() {
        for (Event event : this.eventList) {
            event.clearAllContacts();
        }
    }
}
