package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;


/**
 * Wraps all event data.
 * Duplicates are not allowed (by .isSameEvent comparison).
 */
public class EventBook implements ReadOnlyEventBook {

    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private final ObservableList<Event> unmodifiableEvents = FXCollections.unmodifiableObservableList(events);

    public EventBook() {}

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setAll(events);
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        setEvents(newData.getEventList());
    }

    //// event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the event book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.stream().anyMatch(event::isSameEvent);
    }


    /**
     * Adds an event to the event book.
     * The event must not already exist in the event book.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Removes {@code key} from this {@code EventBook}.
     * {@code key} must exist in the event book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    /**
     * Updates the event at {@code oldEventIndex} to {@code newEvent}.
     */
    public void updateEvent(Event newEvent, int oldEventIndex) {
        events.set(oldEventIndex, newEvent);
    }

    //// util methods

    @Override
    public ObservableList<Event> getEventList() {
        return unmodifiableEvents;
    }

    @Override
    public int getEventListLength() {
        return events.size();
    }

    @Override
    public String toString() {
        return events.size() + " events";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventBook // instanceof handles nulls
                && events.equals(((EventBook) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
