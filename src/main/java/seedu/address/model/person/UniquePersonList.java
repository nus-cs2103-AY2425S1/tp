package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns the number of contacts in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains a person with equivalent phone number
     */
    public boolean containPhone(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePhone);
    }

    /**
     * Returns true if the list contains person with equivalent email
     */
    public boolean containEmail(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEmail);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Gets the index of the first archived contact in the list.
     */
    public Index getFirstArchivedIndex() {
        for (int i = 0; i < size(); i++) {
            Person person = internalList.get(i);
            if (person.isArchived()) {
                Index firstArchivedIndex = Index.fromZeroBased(i);
                return firstArchivedIndex;
            }
        }
        return Index.fromZeroBased(size());
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    /**
     * Reverses the {@code UniquePersonList}.
     * <p>
     * Used when sorting the {@code UniquePersonList} by a specified attribute, in descending order.
     */
    public void reversePersonList() {
        int index = getFirstArchivedIndex().getZeroBased();
        Collections.reverse(internalList.subList(0, index));
        Collections.reverse(internalList.subList(index, internalList.size()));
    }

    /**
     * Checks if the archive status of two contacts are the same.
     */
    public boolean isSameArchiveStatus(Person p1, Person p2) {
        return p1.isArchived() == p2.isArchived();
    }

    /**
     * Sorts the backing list using the {@code Email} attribute of each {@code Person}, in ascending order.
     */
    public void sortByEmail() {
        internalList.sort((p1, p2) -> {
            if (isSameArchiveStatus(p1, p2)) {
                return p1.getEmail().value.compareTo(p2.getEmail().value);
            } else {
                return p1.getArchive().value.compareTo(p2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Name} attribute of each {@code Person}, in ascending order.
     */
    public void sortByName() {
        internalList.sort((p1, p2) -> {
            if (isSameArchiveStatus(p1, p2)) {
                // If both archived or both not archived, compare address as normal.
                return p1.getName().fullName.compareTo(p2.getName().fullName);
            } else {
                //  Not archived (i.e. false) will always come first.
                return p1.getArchive().value.compareTo(p2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Phone} attribute of each {@code Person}, in ascending order.
     */
    public void sortByPhone() {
        internalList.sort((p1, p2) -> {
            if (isSameArchiveStatus(p1, p2)) {
                return p1.getPhone().value.compareTo(p2.getPhone().value);
            } else {
                return p1.getArchive().value.compareTo(p2.getArchive().value);
            }
        });
    }

    /**
     * Sorts the backing list using the {@code Role} attribute of each {@code Person}, in ascending order.
     */
    public void sortByRole() {
        internalList.sort((p1, p2) -> {
            if (isSameArchiveStatus(p1, p2)) {
                return p1.getRole().getValue().compareTo(p2.getRole().getValue());
            } else {
                return p1.getArchive().value.compareTo(p2.getArchive().value);
            }
        });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
