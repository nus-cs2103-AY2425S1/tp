package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


public class UniqueWeddingList implements Iterable<Wedding> {

    private final ObservableList<Wedding> internalList = FXCollections.observableArrayList();
    private final ObservableList<Wedding> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Wedding toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Wedding toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setWeddings(Wedding target, Wedding editedWedding) {
        requireAllNonNull(target, editedWedding);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameWedding(editedWedding) && contains(editedWedding)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedWedding);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Wedding toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setWeddings(List<Wedding> weddings) {
        requireAllNonNull(weddings);
        if (!weddingsAreUnique(weddings)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(weddings);
    }

    /**
     * Replaces the contents of this list with {@code weddings}.
     * {@code weddings} must not contain duplicate weddings.
     */
    public void setWedding(List<Wedding> weddings) {
        requireAllNonNull(weddings);
        if (!weddingsAreUnique(weddings)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(weddings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Wedding> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Wedding> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueWeddingList)) {
            return false;
        }

        UniqueWeddingList otherUniqueWeddingList = (UniqueWeddingList) other;
        return internalList.equals(otherUniqueWeddingList.internalList);
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
     * Returns true if {@code weddings} contains only unique weddings.
     */
    private boolean weddingsAreUnique(List<Wedding> weddings) {
        for (int i = 0; i < weddings.size() - 1; i++) {
            for (int j = i + 1; j < weddings.size(); j++) {
                if (weddings.get(i).isSameWedding(weddings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
