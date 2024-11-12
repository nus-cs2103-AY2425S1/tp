package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the edu-contacts level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class EduContacts implements ReadOnlyEduContacts {

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

    public EduContacts() {}

    /**
     * Creates an EduContacts using the Persons in the {@code toBeCopied}
     */
    public EduContacts(ReadOnlyEduContacts toBeCopied) {
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
     * Resets the existing data of this {@code EduContacts} with {@code newData}.
     */
    public void resetData(ReadOnlyEduContacts newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in EduContacts.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to EduContacts.
     * The person must not already exist in EduContacts.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a module to the person in EduContacts.
     * The person must not already exist in EduContacts.
     */
    public void addModule(Person person, Module module) {
        requireNonNull(person);
        requireNonNull(module);

        if (!hasPerson(person)) {
            throw new IllegalArgumentException("Person not found in EduContacts.");
        }

        if (persons.contains(person)) {
            person.addModule(module);
        }

        Person updatedPerson = person.addModule(module);
        setPerson(person, updatedPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in EduContacts.
     * The person identity of {@code editedPerson} must not be the same as another existing person in EduContacts.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code EduContacts}.
     * {@code key} must exist in EduContacts.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }


    /**
     * Removes {@code module} from {@code person} in EduContacts.
     * {@code person} must exist in EduContacts.
     */
    public void removeModule(Person person, Module module) {
        requireNonNull(person);
        requireNonNull(module);

        if (!hasPerson(person)) {
            throw new IllegalArgumentException("Person not found in EduContacts.");
        }

        if (!person.getModules().contains(module)) {
            throw new IllegalArgumentException("Module not found in person's list.");
        }

        Person updatedPerson = person.deleteModule(module);
        setPerson(person, updatedPerson);
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
        if (!(other instanceof EduContacts)) {
            return false;
        }

        EduContacts otherEduContacts = (EduContacts) other;
        return persons.equals(otherEduContacts.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
