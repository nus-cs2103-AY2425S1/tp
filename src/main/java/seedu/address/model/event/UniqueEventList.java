package seedu.address.model.event;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Event toAdd) {
    // To implement
    }


    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Event> persons) {
        return true; //Implement in future
    }
}

