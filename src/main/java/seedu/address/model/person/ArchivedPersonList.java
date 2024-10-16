package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
/**
 * A list of archived persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}.
 */
public class ArchivedPersonList implements Iterable<Person> {

    private final ObservableList<Person> archivedList = FXCollections.observableArrayList();
    private final ObservableList<Person> unmodifiableArchivedList =
            FXCollections.unmodifiableObservableList(archivedList);

    /**
     * Returns true if the list contains an equivalent archived person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return archivedList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the archived list.
     * The person must not already exist in the archived list.
     */
    public void addArchivedPerson(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        archivedList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the archived list.
     * The person must exist in the archived list.
     */
    public void removeArchivedPerson(Person toRemove) {
        requireNonNull(toRemove);
        if (!archivedList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setArchivedPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        archivedList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return unmodifiableArchivedList;
    }

    @Override
    public Iterator<Person> iterator() {
        return archivedList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArchivedPersonList)) {
            return false;
        }

        ArchivedPersonList otherArchivedPersonList = (ArchivedPersonList) other;
        return archivedList.equals(otherArchivedPersonList.archivedList);
    }

    @Override
    public int hashCode() {
        return archivedList.hashCode();
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
