package seedu.address.model.types.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.types.common.DateTimeUtil;
import seedu.address.model.types.event.exceptions.DuplicateEventException;
import seedu.address.model.types.event.exceptions.EventNotFoundException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * An event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * events uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueEventList. However, the removal of an event uses Event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Resorts Events
     */
    public void sortEvents() {
        internalList.sort((event1, event2) -> {
            long remainingTime1 = getEventTimeRemaining(event1);
            long remainingTime2 = getEventTimeRemaining(event2);

            // Compare based on the remaining time in minutes
            if (remainingTime1 == 0 && remainingTime2 == 0) {
                return 0; // Both events start now
            } else if (remainingTime1 == 0) {
                return -1; // event1 starts now, it comes first
            } else if (remainingTime2 == 0) {
                return 1; // event2 starts now, it comes first
            }

            // If both events are in the past
            if (remainingTime1 < 0 && remainingTime2 < 0) {
                return Long.compare(remainingTime1, remainingTime2); // Sort by which event is further in the past
            } else if (remainingTime1 < 0) {
                return 1; // event1 is in the past, comes after event2
            } else if (remainingTime2 < 0) {
                return -1; // event2 is in the past, comes after event1
            }

            // Sort based on remaining time in minutes
            return Long.compare(remainingTime1, remainingTime2);
        });
    }

    private long getEventTimeRemaining(Event event) {
        LocalDateTime eventStart = event.getStartTime().toLocalDateTime();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        Duration duration = Duration.between(now, eventStart);
        return duration.toMillis();
    }

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds an event to the list.
     * The event must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
        sortEvents();
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the list.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the list.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, editedEvent);
        sortEvents();
    }

    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sortEvents();
    }

    /**
     * Replaces the contents of this list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
        sortEvents();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueEventList)) {
            return false;
        }

        UniqueEventList otherUniqueEventList = (UniqueEventList) other;
        return internalList.equals(otherUniqueEventList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code events} contains only unique events.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
