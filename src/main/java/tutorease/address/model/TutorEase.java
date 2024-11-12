package tutorease.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePerson comparison).
 */
public class TutorEase implements ReadOnlyTutorEase {

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

    public TutorEase() {}

    /**
     * Creates an TutorEase using the Persons in the {@code toBeCopied}.
     */
    public TutorEase(ReadOnlyTutorEase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code TutorEase} with {@code newData}.
     */
    public void resetData(ReadOnlyTutorEase newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    // person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if there is another person in the address book with the same phone number as {@code person}.
     */
    public boolean hasSamePhone(Person person) {
        requireNonNull(person);

        for (Person existingPerson : persons) {
            if (existingPerson.hasSamePhone(person)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there is another person in the address book with the same email as {@code person}.
     */
    public boolean hasSameEmail(Person person) {
        requireNonNull(person);

        for (Person existingPerson : persons) {
            if (existingPerson.hasSameEmail(person)) {
                return true;
            }
        }
        return false;
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
     * Returns the person with the specified name.
     *
     * @param name The name of the person to retrieve.
     * @return The person with the specified name.
     */
    public Person getPerson(String name) {
        return persons.getPerson(name);
    }

    /**
     * Removes {@code key} from this {@code TutorEase}.
     * {@code key} must exist in the address book.
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
        if (!(other instanceof TutorEase)) {
            return false;
        }

        TutorEase otherTutorEase = (TutorEase) other;
        return persons.equals(otherTutorEase.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
