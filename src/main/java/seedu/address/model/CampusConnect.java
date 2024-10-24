package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Stack;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the CampusConnect level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CampusConnect implements ReadOnlyCampusConnect {

    private final UniquePersonList persons;
    private final Stack<ReadOnlyCampusConnect> prev = new Stack<>();
    private final Stack<ReadOnlyCampusConnect> future = new Stack<>();

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

    public CampusConnect() {}

    /**
     * Creates an CampusConnect using the Persons in the {@code toBeCopied}
     */
    public CampusConnect(ReadOnlyCampusConnect toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Stores the current states of the CampusConnect.
     */
    public void saveCurrentState() {
        ReadOnlyCampusConnect newCampusConnect = new CampusConnect(this);
        prev.add(newCampusConnect);
        future.clear();
    }

    /**
     * Recover from previous states
     */
    public ReadOnlyCampusConnect recoverPreviousState() {
        if (!prev.isEmpty()) {
            ReadOnlyCampusConnect out = prev.pop();
            future.push(new CampusConnect(this));
            assert out != null;
            return out;
        } else {
            return new CampusConnect(this);
        }
    }

    /**
     * Recover previously undone states
     */
    public ReadOnlyCampusConnect recoverUndoneState() {
        if (!future.isEmpty()) {
            ReadOnlyCampusConnect out = future.pop();
            prev.push(new CampusConnect(this));
            assert out != null;
            return out;
        } else {
            return new CampusConnect(this);
        }
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code CampusConnect} with {@code newData}.
     */
    public void resetData(ReadOnlyCampusConnect newData) {
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
     * Adds a person to the specific position of the CampusConnect.
     *
     */
    public void addPerson(Person p, int ind) {
        persons.add(ind, p);
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
     * Removes {@code key} from this {@code CampusConnect}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Delete a tag from a person.
     */
    public void removePersonTag(Person p, Tag t) {
        persons.deletePersonTag(p, t);
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
    public ObservableList<Tag> getTagList() {
        return persons.asTagList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CampusConnect otherCampusConnect)) {
            return false;
        }

        return persons.equals(otherCampusConnect.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
