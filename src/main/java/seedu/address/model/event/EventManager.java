package seedu.address.model.event;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventException;
import seedu.address.model.person.Person;

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
    public void addEvent(Event e) {
        this.eventList.add(e);
    }

    public void removeEvent(Event e) {
        // probably need to add a check here
        this.eventList.remove(e);
    }

    public void setEvent(Event target, Event editedEvent) {
        this.eventList.setEvent(target, editedEvent);
    }

    @Override
    public ObservableList<Event> getEventList() {
        return eventList.asUnmodifiableObservableList();
    }
}
