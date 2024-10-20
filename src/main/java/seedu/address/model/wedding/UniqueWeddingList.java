package seedu.address.model.wedding;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.exceptions.DuplicateWeddingException;
import seedu.address.model.person.exceptions.PersonNotAssignedToWeddingException;
import seedu.address.model.person.exceptions.WeddingNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class UniqueWeddingList implements Iterable<Wedding> {
    private final ObservableList<Wedding> internalList = FXCollections.observableArrayList();
    private final ObservableList<Wedding> internalUnmodifiableList
            = FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Wedding toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWedding);
    }

    /**
     * Adds a new Wedding to the list. Throws an error if the Wedding already exists
     * @param wedding The Wedding to add
     */
    public void addWedding (Wedding wedding) {
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
    public void removeWedding (Wedding wedding) {
        requireNonNull(wedding);
        if (!internalList.remove(wedding)) {
            throw new WeddingNotFoundException();
        }
    }

    /**
     * Assigns a Person to a Wedding. Throws an error if the Wedding cannot be found.
     * @param wedding
     * @param assignee
     */
    public void assignToWedding (Wedding wedding, Person assignee) {
        requireNonNull(wedding);
        requireNonNull(assignee);

        if (!contains(wedding)) {
            throw new WeddingNotFoundException();
        }

        int index = internalList.indexOf(wedding);

        String name = wedding.getName();
        String date = wedding.getDate();
        List<PersonId> assignees = wedding.getAssignees();
        assignees.add(assignee.getId());

        Wedding newWedding = new Wedding(name, date, assignees);
        internalList.set(index, newWedding);
    }

    /**
     * Removes the specified Person from the specified Wedding's assignee list
     * @param wedding
     * @param assignee
     */
    public void unassignFromWedding (Wedding wedding, Person assignee) {
        requireNonNull(wedding);
        requireNonNull(assignee);

        if (!contains(wedding)) {
            throw new WeddingNotFoundException();
        }

        int index = internalList.indexOf(wedding);

        String name = wedding.getName();
        String date = wedding.getDate();
        List<PersonId> assignees = wedding.getAssignees();
        if (!assignees.contains(assignee.getId())) {
            throw new PersonNotAssignedToWeddingException();
        }
        assignees.remove(assignee.getId());

        Wedding newWedding = new Wedding(name, date, assignees);
        internalList.set(index, newWedding);
    }

    @Override
    public Iterator<Wedding> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Wedding> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }
}
