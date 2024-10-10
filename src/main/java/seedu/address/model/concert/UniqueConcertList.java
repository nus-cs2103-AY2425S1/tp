package seedu.address.model.concert;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.concert.exceptions.ConcertNotFoundException;
import seedu.address.model.concert.exceptions.DuplicateConcertException;

/**
 * A list of concerts that enforces uniqueness between its elements and does not allow nulls. A
 * concert is considered unique by comparing using {@code Concert#isSameConcert(Concert)}. As such,
 * adding and updating of concerts uses Concert#isSameConcert(Concert) for equality so as to ensure
 * that the concert being added or updated is unique in terms of identity in the UniqueConcertList.
 * However, the removal of a concert uses Concert#equals(Object) so as to ensure that the concert
 * with exactly the same fields will be removed. In this case the equals and isSameConcert return
 * the same value.
 *
 * Supports a minimal set of list operations.
 *
 * @see Concert#isSameconcert(Concert)
 */
public class UniqueConcertList implements Iterable<Concert> {

    private final ObservableList<Concert> internalList = FXCollections.observableArrayList();
    private final ObservableList<Concert> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent concert as the given argument.
     */
    public boolean contains(Concert toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameConcert);
    }

    /**
     * Adds a concert to the list. The concert must not already exist in the list.
     */
    public void add(Concert toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateConcertException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the concert {@code target} in the list with {@code editedconcert}. {@code target}
     * must exist in the list. The concert identity of {@code editedconcert} must not be the same as
     * another existing concert in the list.
     */
    public void setconcert(Concert target, Concert editedconcert) {
        requireAllNonNull(target, editedconcert);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ConcertNotFoundException();
        }

        if (!target.isSameConcert(editedconcert) && contains(editedconcert)) {
            throw new DuplicateConcertException();
        }

        internalList.set(index, editedconcert);
    }

    /**
     * Removes the equivalent concert from the list. The concert must exist in the list.
     */
    public void remove(Concert toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ConcertNotFoundException();
        }
    }

    public void setConcerts(UniqueConcertList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code concerts}. {@code concerts} must not contain
     * duplicate concerts.
     */
    public void setConcerts(List<Concert> concerts) {
        requireAllNonNull(concerts);
        if (!concertsAreUnique(concerts)) {
            throw new DuplicateConcertException();
        }

        internalList.setAll(concerts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Concert> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Concert> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueConcertList)) {
            return false;
        }

        UniqueConcertList otherUniqueconcertList = (UniqueConcertList) other;
        return internalList.equals(otherUniqueconcertList.internalList);
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
     * Returns true if {@code concerts} contains only unique concerts.
     */
    private boolean concertsAreUnique(List<Concert> concerts) {
        for (int i = 0; i < concerts.size() - 1; i++) {
            for (int j = i + 1; j < concerts.size(); j++) {
                if (concerts.get(i).isSameConcert(concerts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
