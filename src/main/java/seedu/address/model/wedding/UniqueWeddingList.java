package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.PersonNotAssignedToWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

/**
 * Contains a list of Weddings accessible by an ObservableList
 */
public class UniqueWeddingList implements Iterable<Wedding> {
    private final ObservableList<Wedding> internalList = FXCollections.observableArrayList();
    private final ObservableList<Wedding> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks if a Wedding is in the UniqueWeddingList
     * @param toCheck the Wedding to check
     * @return
     */
    public boolean contains(Wedding toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWedding);
    }

    /**
     * Adds a new Wedding to the list. Throws an error if the Wedding already exists
     * @param wedding The Wedding to add
     */
    public void addWedding(Wedding wedding) {
        requireNonNull(wedding);
        if (contains(wedding)) {
            throw new DuplicateWeddingException();
        }
        internalList.add(wedding);
    }

    /**
     * Removes a Wedding from the list. Throws an error if the Wedding cannot be found
     * @param wedding The Wedding to remove
     */
    public void removeWedding(Wedding wedding) {
        requireNonNull(wedding);
        if (!internalList.remove(wedding)) {
            throw new WeddingNotFoundException();
        }
    }

    /**
     * Replaces a wedding with a new wedding. Throws an error if the old wedding cannot be found
     * @param wedding The wedding to replace
     * @param newWedding The new wedding to replace with
     */
    public void setWedding(Wedding wedding, Wedding newWedding) {
        requireAllNonNull(wedding, newWedding);

        if (!contains(wedding)) {
            throw new WeddingNotFoundException();
        }
        int index = internalList.indexOf(wedding);

        internalList.set(index, newWedding);
    }

    /**
     * Replaces the contents of this list with {@code weddings}.
     * {@code weddings} must not contain duplicate weddings.
     */
    public void setWeddings(List<Wedding> weddings) {
        requireAllNonNull(weddings);
        if (!weddingsAreUnique(weddings)) {
            throw new DuplicateWeddingException();
        }

        internalList.setAll(weddings);
    }

    /**
     * Assigns a Person to a Wedding. Throws an error if the Wedding cannot be found.
     * @param wedding
     * @param assignee
     */
    public void assignToWedding(Wedding wedding, Person assignee) {
        requireNonNull(wedding);
        requireNonNull(assignee);

        if (!contains(wedding)) {
            throw new WeddingNotFoundException();
        }

        int index = internalList.indexOf(wedding);

        WeddingName weddingName = wedding.getWeddingName();
        WeddingDate date = wedding.getWeddingDate();
        List<PersonId> assignees = wedding.getAssignees();
        assignees.add(assignee.getId());

        Wedding newWedding = new Wedding(weddingName, date, assignees);
        internalList.set(index, newWedding);
    }

    /**
     * Removes the specified Person from the specified Wedding's assignee list
     * @param wedding
     * @param assignee
     */
    public void unassignFromWedding(Wedding wedding, Person assignee) {
        requireNonNull(wedding);
        requireNonNull(assignee);

        if (!contains(wedding)) {
            throw new WeddingNotFoundException();
        }

        int index = internalList.indexOf(wedding);

        WeddingName weddingName = wedding.getWeddingName();
        WeddingDate date = wedding.getWeddingDate();
        List<PersonId> assignees = wedding.getAssignees();
        if (!assignees.contains(assignee.getId())) {
            throw new PersonNotAssignedToWeddingException();
        }
        assignees.remove(assignee.getId());

        Wedding newWedding = new Wedding(weddingName, date, assignees);
        internalList.set(index, newWedding);
    }

    @Override
    public Iterator<Wedding> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Wedding> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
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
