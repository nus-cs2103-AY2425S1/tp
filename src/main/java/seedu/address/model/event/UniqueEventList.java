package seedu.address.model.event;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * A event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * events uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueEventList. However, the removal of a event uses Event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        return false; //Implement in future
    }

    /**
     * Adds an event to the list.
     * The event must not already exist in the list.
     */
    public void add(Event toAdd) {
    // To implement
    }


    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        //To implement
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
        //To implement in future
        return true;
    }

    @Override
    public int hashCode() {
        return 0; //Implement in future
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code events} contains only unique persons.
     */
    private boolean personsAreUnique(List<Event> persons) {
        return true; //Implement in future
    }
}

