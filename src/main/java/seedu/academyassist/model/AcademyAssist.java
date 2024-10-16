package seedu.academyassist.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AcademyAssist implements ReadOnlyAcademyAssist {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AcademyAssist() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AcademyAssist(ReadOnlyAcademyAssist toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAcademyAssist newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Sorts persons in this address book by name, in alphabetical order.
     */
    public void sortPersonsByName() {
        persons.sortPersonsByName();
    }

    /**
     * Sorts persons in this address book by class, in alphabetical order.
     */
    public void sortPersonsByClass() {
        persons.sortPersonsByClass();
    }

    /**
     * Returns True if this {@code AddressBook} contains Person with given {@code Ic}
     */
    public boolean hasPersonWithIc(Ic ic) {
        return persons.hasPersonWithIc(ic);
    }

    /**
     * Returns the {@code Person} in this {@code AddressBook} with the given {@code Ic}
     */
    public Person getPersonWithIc(Ic ic) {
        return persons.getPersonWithIc(ic);
    }

    /**
     * Returns true if {@code Person} is already taking this {@code subject}
     */
    public boolean personDuplicateClass(Subject subject, Person student) {
        return student.getSubjects().contains(subject);
    }

    /**
     * Adds {@code subject} to {@code person}
     */
    public void addSubjectToPerson(Subject subject, Person person) {
        persons.remove(person);
        persons.add(person.addSubject(subject));
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AcademyAssist)) {
            return false;
        }

        AcademyAssist otherAddressBook = (AcademyAssist) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }



}
