package seedu.address.model.concert;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.concert.exceptions.ConcertContactNotFoundException;
import seedu.address.model.concert.exceptions.ConcertNotFoundException;
import seedu.address.model.concert.exceptions.DuplicateConcertContactException;
import seedu.address.model.concert.exceptions.DuplicateConcertException;

/**
 * A list of concertContacts that enforces uniqueness between its elements and does not allow nulls. A
 * concertContact is considered unique by comparing using {@code ConcertContact#isSameConcertContact(ConcertContact)}.
 * As such, adding and updating of concerts uses ConcertContact#isSameConcertConcert(ConcertContact) for equality
 * to ensure that the concertContact being added or updated is unique in terms of identity in the
 * UniqueConcertContactList.
 * However, the removal of a concertContact uses ConcertContact#equals(Object) to ensure that the concertContact
 * with exactly the same fields will be removed. In this case the equals and isSameConcertContact return
 * the same value.
 *
 * Supports a minimal set of list operations.
 *
 * @see ConcertContact#isSameConcertContact(ConcertContact)
 */
public class UniqueConcertContactList implements Iterable<ConcertContact> {
    private final ObservableList<ConcertContact> internalList = FXCollections.observableArrayList();
    private final ObservableList<ConcertContact> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent concertContact as the given argument.
     */
    public boolean contains(ConcertContact toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameConcertContact);
    }

    /**
     * Adds a concertContact to the list. The concertContact must not already exist in the list.
     */
    public void add(ConcertContact toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateConcertContactException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the concertContact {@code target} in the list with {@code editedConcertContact}. {@code target}
     * must exist in the list. The concertContact identity of {@code editedConcertContact} must not be the same as
     * another existing concertContact in the list.
     */
    public void setConcertContact(ConcertContact target, ConcertContact editedConcertContact) {
        requireAllNonNull(target, editedConcertContact);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ConcertNotFoundException();
        }

        if (!target.isSameConcertContact(editedConcertContact) && contains(editedConcertContact)) {
            throw new DuplicateConcertException();
        }

        internalList.set(index, editedConcertContact);
    }

    /**
     * Removes the equivalent concertContact from the list. The concertContact must exist in the list.
     */
    public void remove(ConcertContact toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ConcertContactNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}. {@code replacement} must not contain
     * duplicate concertContacts.
     */
    public void setConcertContacts(UniqueConcertContactList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code concertContacts}. {@code concertContacts} must not contain
     * duplicate concerts.
     */
    public void setConcertContacts(List<ConcertContact> concertContacts) {
        requireAllNonNull(concertContacts);
        if (!concertContactsAreUnique(concertContacts)) {
            throw new DuplicateConcertContactException();
        }

        internalList.setAll(concertContacts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ConcertContact> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ConcertContact> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueConcertContactList)) {
            return false;
        }

        UniqueConcertContactList otherUniqueList = (UniqueConcertContactList) other;
        return internalList.equals(otherUniqueList.internalList);
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
     * Returns true if {@code concertContacts} contains only unique concerts.
     */
    private boolean concertContactsAreUnique(List<ConcertContact> concertContacts) {
        for (int i = 0; i < concertContacts.size() - 1; i++) {
            for (int j = i + 1; j < concertContacts.size(); j++) {
                if (concertContacts.get(i).isSameConcertContact(concertContacts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
