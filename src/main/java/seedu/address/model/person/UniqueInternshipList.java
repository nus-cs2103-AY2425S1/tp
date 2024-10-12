package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateInternshipException;
import seedu.address.model.person.exceptions.InternshipNotFoundException;

/**
 * A list of internships that enforces uniqueness between its elements and does not allow nulls.
 * An internship is considered unique by comparing using {@code Internship#isSame(Internship)}.
 * Adding, updating, or removing of internships follows this uniqueness constraint.
 */
public class UniqueInternshipList implements Iterable<Internship> {

    private final ObservableList<Internship> internalList = FXCollections.observableArrayList();
    private final ObservableList<Internship> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent internship as the given argument.
     */
    public boolean contains(Internship toCheck) {
        assert toCheck != null : "Internship toCheck should not be null";
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds an internship to the list.
     * The internship must not already exist in the list.
     * @throws DuplicateInternshipException if the internship to add already exists.
     */
    public void add(Internship toAdd) {
        assert toAdd != null : "Internship toAdd should not be null";

        if (contains(toAdd)) {
            throw new DuplicateInternshipException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the internship {@code target} in the list with {@code editedInternship}.
     * {@code target} must exist in the list.
     * The internship identity of {@code editedInternship} must not be the same
     * as another existing internship in the list.
     * @throws InternshipNotFoundException if {@code target} is not found in the list.
     * @throws DuplicateInternshipException if the internship identity conflicts with another in the list.
     */
    public void setInternship(Internship target, Internship editedInternship) {
        requireAllNonNull(target, editedInternship);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InternshipNotFoundException();
        }

        if (!target.isSame(editedInternship) && contains(editedInternship)) {
            throw new DuplicateInternshipException();
        }

        internalList.set(index, editedInternship);
    }

    /**
     * Removes the equivalent internship from the list.
     * The internship must exist in the list.
     * @throws InternshipNotFoundException if the internship to remove is not found in the list.
     */
    public void remove(Internship toRemove) {
        assert toRemove != null : "Internship to remove should not be null";
        if (!internalList.remove(toRemove)) {
            throw new InternshipNotFoundException();
        }
    }

    /**
     * Checks if this list is empty.
     */
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * Returns the size of this list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the contents of this list with {@code internships}.
     * {@code internships} must not contain duplicate internships.
     * @throws DuplicateInternshipException if there are duplicates in the new list.
     */
    public UniqueInternshipList setInternships(List<Internship> internships) {
        requireAllNonNull(internships);
        assert internshipsAreUnique(internships) : "All internships in the list should be unique";

        if (!internshipsAreUnique(internships)) {
            throw new DuplicateInternshipException();
        }

        internalList.setAll(internships);
        return this;
    }

    /**
     * Replaces the contents of this list with the contents of {@code internships}.
     */
    public UniqueInternshipList setInternships(UniqueInternshipList internships) {
        assert internships != null : "Internships list should not be null";
        internalList.setAll(internships.internalList);
        return this;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Internship> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Internship> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueInternshipList)) {
            return false;
        }

        UniqueInternshipList otherList = (UniqueInternshipList) other;
        return internalList.equals(otherList.internalList);
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
     * Returns true if {@code internships} contains only unique internships.
     */
    private boolean internshipsAreUnique(List<Internship> internships) {
        for (int i = 0; i < internships.size() - 1; i++) {
            for (int j = i + 1; j < internships.size(); j++) {
                if (internships.get(i).isSame(internships.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
