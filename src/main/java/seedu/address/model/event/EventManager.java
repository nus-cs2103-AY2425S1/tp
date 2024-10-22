package seedu.address.model.event;

import javafx.collections.ObservableList;

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
}
