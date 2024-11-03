package seedu.hiredfiredpro.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.hiredfiredpro.commons.util.ToStringBuilder;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.model.person.UniquePersonList;

/**
 * Wraps all data at the hiredfiredpro level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class HiredFiredPro implements ReadOnlyHiredFiredPro {

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

    public HiredFiredPro() {}

    /**
     * Creates a HiredFiredPro list using the Persons in the {@code toBeCopied}
     */
    public HiredFiredPro(ReadOnlyHiredFiredPro toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations


    /**
     * Resets the existing data of this {@code HiredFiredPro} with {@code newData}.
     */
    public void resetData(ReadOnlyHiredFiredPro newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the hiredfiredpro.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the hiredfiredpro.
     * The person must not already exist in the hiredfiredpro.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the hiredfiredpro.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the hiredfiredpro.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }


    /**
     * Replaces the current list of persons with the given list.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Removes {@code key} from this {@code HiredFiredPro}.
     * {@code key} must exist in the hiredfiredpro.
     */
    public void removePerson(Person key) {
        persons.remove(key);
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
        if (!(other instanceof HiredFiredPro)) {
            return false;
        }

        HiredFiredPro otherHiredFiredPro = (HiredFiredPro) other;
        return persons.equals(otherHiredFiredPro.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
