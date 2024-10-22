package seedu.academyassist.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.academyassist.commons.util.CollectionUtil;
import seedu.academyassist.model.person.exceptions.DuplicatePersonException;
import seedu.academyassist.model.person.exceptions.PersonNotFoundException;

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
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
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
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

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
        CollectionUtil.requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns true if there is a person in the list with the given IC.
     */
    public boolean hasPersonWithIc(Ic ic) {
        requireNonNull(ic);
        return internalList.stream().anyMatch(person -> person.getIc().equals(ic));
    }

    /**
     * Returns true if there is a {@code Person} in the list with the given {@code StudentId}.
     */
    public boolean hasPersonWithStudentId(StudentId studentId) {
        requireNonNull(studentId);
        return internalList.stream().anyMatch(person -> person.getStudentId().equals(studentId));
    }

    /**
     * Returns the student in the list with the given IC.
     * Throws PersonNotFoundException if no person with the given IC is found.
     */
    public Person getPersonWithIc(Ic ic) {
        requireNonNull(ic);
        return internalList.stream()
                .filter(person -> person.getIc().equals(ic))
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Returns the {@code Person} in the list with the given {@code StudentId}.
     * Throws {@link PersonNotFoundException} if no person with the given {@code StudentId} is found.
     */
    public Person getPersonWithStudentId(StudentId studentId) {
        requireNonNull(studentId);
        return internalList.stream()
                .filter(person -> person.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);
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

    /**
     * Sorts the internal list of persons by their names in-place.
     */
    public void sortPersonsByName() {
        internalList.sort(Comparator.comparing(person -> person.getName().toString()));
    }

    /**
     * Sorts the internal list of persons by their classes in-place. Sort based on lexicographically smallest subject.
     */
    public void sortPersonsByClass() {
        internalList.sort(Comparator.comparing(person ->
                person.getSubjects().stream().map(Subject::toString)
                        .sorted()
                        .findFirst()
                        .orElse("")
        ));
    }

    /**
     * Sorts the internal list of persons by their studentIds in-place.
     */
    public void sortPersonsById() {
        internalList.sort(Comparator.comparing(person -> person.getStudentId().toString()));
    }
}
